package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.CarteiraRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.CarteiraResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.SaldoDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.repositories.CarteiraRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarteiraService {

    private static final double VALOR_FICHA = 1.0;

    private final CarteiraRepository carteiraRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    private CarteiraResponseDTO convertToResponseDTO(CarteiraEntity carteira) {
        return objectMapper.convertValue(carteira, CarteiraResponseDTO.class);
    }

    public void atualizarCarteira(CarteiraEntity carteira) throws RegraDeNegocioException {
        carteiraRepository.save(carteira);
    }

    public CarteiraEntity adicionarCarteira(JogadorEntity jogador) throws RegraDeNegocioException {
        log.info("Criando carteira padrão para o jogador ID: {}", jogador.getIdJogador());

        CarteiraRequestDTO defaultCarteiraRequestDTO = new CarteiraRequestDTO();

        CarteiraEntity carteira = objectMapper.convertValue(defaultCarteiraRequestDTO, CarteiraEntity.class);

        carteira.setJogador(jogador);

        CarteiraEntity carteiraAdicionada = carteiraRepository.save(carteira);
        log.info("Carteira padrão para jogador ID {} adicionada com sucesso! ID da Carteira: {}", jogador.getIdJogador(), carteiraAdicionada.getIdCarteira());
        return carteiraAdicionada;
    }

    public List<CarteiraResponseDTO> listar() throws RegraDeNegocioException {
        log.info("Listando todas as carteiras...");
        List<CarteiraResponseDTO> carteiras = carteiraRepository.findAll().stream()
                .map(carteira -> {
                    CarteiraResponseDTO carteiraDTO = convertToResponseDTO(carteira);
                    carteiraDTO.setIdJogador(carteira.getJogador().getIdJogador());
                    return carteiraDTO;
                })
                .collect(Collectors.toList());
        log.info("Listagem de carteiras concluída. Total de carteiras: {}", carteiras.size());
        return carteiras;
    }

    public CarteiraResponseDTO buscarCarteiraPorId(Integer idCarteira) throws RegraDeNegocioException {
        log.info("Buscando carteira pelo ID: {}", idCarteira);

        CarteiraEntity carteira = buscarCarteiraEntityPorId(idCarteira);

        log.info("Carteira encontrada: ID {}", carteira.getIdCarteira());
        CarteiraResponseDTO carteiraDTO = convertToResponseDTO(carteira);
        carteiraDTO.setIdJogador(carteira.getJogador().getIdJogador());
        return carteiraDTO;
    }

    private CarteiraEntity buscarCarteiraEntityPorId(Integer idCarteira) throws RegraDeNegocioException {
        return carteiraRepository.findById(idCarteira)
                .orElseThrow(() -> new RegraDeNegocioException("Carteira com ID " + idCarteira + " não encontrada."));
    }

    public CarteiraResponseDTO buscarCarteiraPorIdJogador(Integer idJogador) throws RegraDeNegocioException {
        log.info("Buscando carteira pelo ID do jogador: {}", idJogador);
        CarteiraEntity carteira = carteiraRepository.findByJogadorIdJogador(idJogador).orElseThrow(() ->
                new RegraDeNegocioException("Carteira com ID Jogador " + idJogador + " não encontrada."));
        log.info("Carteira do jogador {} encontrada.", idJogador);

        CarteiraResponseDTO carteiraDTO = convertToResponseDTO(carteira);
        carteiraDTO.setIdJogador(carteira.getJogador().getIdJogador());
        return carteiraDTO;
    }

    public CarteiraResponseDTO atualizarCarteira(Integer idCarteira, CarteiraRequestDTO carteiraAtualizarDTO) throws RegraDeNegocioException {
        CarteiraEntity carteira = buscarCarteiraEntityPorId(idCarteira);
        log.info("Atualizando carteira com ID: {}", idCarteira);

        carteira.setFichas(carteiraAtualizarDTO.getFichas());
        carteira.setDinheiro(carteiraAtualizarDTO.getDinheiro());

        CarteiraEntity carteiraAtualizada = carteiraRepository.save(carteira);

        log.info("Carteira com ID {} atualizada com sucesso.", idCarteira);
        CarteiraResponseDTO carteiraDTO = convertToResponseDTO(carteiraAtualizada);
        carteiraDTO.setIdJogador(carteira.getJogador().getIdJogador());
        return carteiraDTO;
    }

    public SaldoDTO depositarDinheiro(Integer idCarteira, double valor) throws RegraDeNegocioException {
        CarteiraEntity carteira = buscarCarteiraEntityPorId(idCarteira);
        log.info("Depositando R$ {} na carteira ID: {}", valor, idCarteira);
        if (valor <= 0) {
            throw new RegraDeNegocioException("Valor de depósito inválido: R$ " + valor);
        }
        carteira.setDinheiro(carteira.getDinheiro() + valor);
        CarteiraEntity carteiraAtualizada = carteiraRepository.save(carteira);
        log.info("Depósito de R$ {} realizado com sucesso na carteira ID {}. Saldo atual: R$ {}", valor, idCarteira, carteiraAtualizada.getDinheiro());

        emailService.sendEmailDepositarCarteira(carteira, valor);
        return objectMapper.convertValue(carteiraAtualizada, SaldoDTO.class);
    }

    public SaldoDTO sacarDinheiro(Integer idCarteira, double valor) throws RegraDeNegocioException {
        CarteiraEntity carteira = buscarCarteiraEntityPorId(idCarteira);
        log.info("Sacando R$ {} da carteira ID: {}", valor, idCarteira);
        if (valor <= 0) {
            throw new RegraDeNegocioException("Valor de saque inválido: R$ " + valor);
        }
        if (carteira.getDinheiro() < valor) {
            throw new RegraDeNegocioException("Saldo insuficiente para sacar R$ " + valor + " reais. Saldo atual: R$ " + carteira.getDinheiro());
        }
        carteira.setDinheiro(carteira.getDinheiro() - valor);
        CarteiraEntity carteiraAtualizada = carteiraRepository.save(carteira);
        log.info("Saque de R$ {} realizado com sucesso da carteira ID {}. Saldo atual: R$ {}", valor, idCarteira, carteiraAtualizada.getDinheiro());

        emailService.sendEmailSacarCarteira(carteira, valor);
        return objectMapper.convertValue(carteiraAtualizada, SaldoDTO.class);
    }

    public SaldoDTO comprarFichas(Integer idCarteira, int quantidadeFicha) throws RegraDeNegocioException {
        CarteiraEntity carteira = buscarCarteiraEntityPorId(idCarteira);
        log.info("Comprando {} fichas para a carteira ID: {}", quantidadeFicha, idCarteira);
        if (quantidadeFicha <= 0) {
            throw new RegraDeNegocioException("Quantidade de fichas para compra deve ser positiva: " + quantidadeFicha);
        }
        double custoTotal = quantidadeFicha * VALOR_FICHA;
        if (carteira.getDinheiro() < custoTotal) {
            throw new RegraDeNegocioException("Dinheiro insuficiente para comprar as fichas. Custo total: R$ " + custoTotal + ". Saldo atual: R$ " + carteira.getDinheiro());
        }
        carteira.setFichas(carteira.getFichas() + quantidadeFicha);
        carteira.setDinheiro(carteira.getDinheiro() - custoTotal);
        CarteiraEntity carteiraAtualizada = carteiraRepository.save(carteira);
        log.info("Compra de {} fichas realizada com sucesso para a carteira ID {}. Fichas atuais: {}, Saldo atual: R$ {}", quantidadeFicha, idCarteira, carteiraAtualizada.getFichas(), carteiraAtualizada.getDinheiro());
        return objectMapper.convertValue(carteiraAtualizada, SaldoDTO.class);
    }

    public SaldoDTO venderFichas(Integer idCarteira, int quantidadeFicha) throws RegraDeNegocioException {
        CarteiraEntity carteira = buscarCarteiraEntityPorId(idCarteira);
        log.info("Vendendo {} fichas da carteira ID: {}", quantidadeFicha, idCarteira);
        if (quantidadeFicha <= 0) {
            throw new RegraDeNegocioException("Quantidade de fichas para venda deve ser positiva: " + quantidadeFicha);
        }
        if (carteira.getFichas() < quantidadeFicha) {
            throw new RegraDeNegocioException("Quantidade de fichas insuficiente para vender. Fichas disponíveis: " + carteira.getFichas());
        }
        carteira.setFichas(carteira.getFichas() - quantidadeFicha);
        carteira.setDinheiro(carteira.getDinheiro() + (quantidadeFicha * VALOR_FICHA));
        CarteiraEntity carteiraAtualizada = carteiraRepository.save(carteira);
        log.info("Venda de {} fichas realizada com sucesso da carteira ID {}. Fichas atuais: {}, Saldo atual: R$ {}", quantidadeFicha, idCarteira, carteiraAtualizada.getFichas(), carteiraAtualizada.getDinheiro());
        return objectMapper.convertValue(carteiraAtualizada, SaldoDTO.class);
    }
}