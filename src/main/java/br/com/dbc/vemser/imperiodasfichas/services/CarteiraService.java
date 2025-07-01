package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.CarteiraRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.CarteiraResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.SaldoDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
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

    private final CarteiraRepository carteiraRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    private CarteiraResponseDTO convertToResponseDTO(CarteiraEntity carteira) {
        return objectMapper.convertValue(carteira, CarteiraResponseDTO.class);
    }

    private CarteiraEntity convertToEntity(CarteiraRequestDTO carteiraRequestDTO) {
        return objectMapper.convertValue(carteiraRequestDTO, CarteiraEntity.class);
    }

    public void atualizarCarteira(CarteiraEntity carteira) throws RegraDeNegocioException {
        carteiraRepository.editar(carteira.getIdCarteira(), carteira);
    }

    public CarteiraResponseDTO adicionarCarteira(Integer idJogador) throws RegraDeNegocioException {
        log.info("Criando carteira padrão para o jogador ID: {}", idJogador);

        CarteiraRequestDTO defaultCarteiraRequestDTO = new CarteiraRequestDTO();

        CarteiraEntity carteira = objectMapper.convertValue(defaultCarteiraRequestDTO, CarteiraEntity.class);

        carteira.setIdJogador(idJogador);

        CarteiraEntity carteiraAdicionada = carteiraRepository.adicionar(carteira);
        log.info("Carteira padrão para jogador ID {} adicionada com sucesso! ID da Carteira: {}", idJogador, carteiraAdicionada.getIdCarteira());
        return convertToResponseDTO(carteiraAdicionada);
    }

    public List<CarteiraResponseDTO> listar() throws RegraDeNegocioException {
        log.info("Listando todas as carteiras...");
        List<CarteiraResponseDTO> carteiras = carteiraRepository.listar().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        log.info("Listagem de carteiras concluída. Total de carteiras: {}", carteiras.size());
        return carteiras;
    }

    public CarteiraResponseDTO buscarCarteiraPorId(Integer idCarteira) throws RegraDeNegocioException {
        log.info("Buscando carteira pelo ID: {}", idCarteira);

        CarteiraEntity carteira = carteiraRepository.buscarPorId(idCarteira);
        if(carteira == null) {
            throw new RegraDeNegocioException("Carteira com ID " + idCarteira + " não encontrada.");
        }

        log.info("Carteira encontrada: ID {}", carteira.getIdCarteira());
        return convertToResponseDTO(carteira);
    }

    public CarteiraResponseDTO buscarCarteiraPorIdJogador(Integer idJogador) throws RegraDeNegocioException {
        if(carteiraRepository.listar().stream().noneMatch(carteira -> carteira.getIdJogador().equals(idJogador))) {
            throw new RegraDeNegocioException("ID do jogador não foi encontrado.");
        }
        log.info("Buscando carteira pelo ID do jogador: {}", idJogador);
        CarteiraEntity carteira = carteiraRepository.buscarCarteiraPorIdJogador(idJogador);
        log.info("Carteira do jogador {} encontrada.", idJogador);
        return convertToResponseDTO(carteira);
    }

    public CarteiraResponseDTO atualizarCarteira(Integer idCarteira, CarteiraRequestDTO carteiraAtualizarDTO) throws RegraDeNegocioException {

        CarteiraResponseDTO carteiraExistente = buscarCarteiraPorId(idCarteira);
        if (carteiraExistente == null) {
            throw new RegraDeNegocioException("Carteira com ID " + idCarteira + " não encontrada.");
        }

        log.info("Atualizando carteira com ID: {}", idCarteira);

        carteiraExistente.setFichas(carteiraAtualizarDTO.getFichas());
        carteiraExistente.setDinheiro(carteiraAtualizarDTO.getDinheiro());

        CarteiraEntity carteiraAtualizada = convertToEntity(carteiraExistente);
        carteiraAtualizada = carteiraRepository.editar(idCarteira, carteiraAtualizada);

        log.info("Carteira com ID {} atualizada com sucesso.", idCarteira);
        return convertToResponseDTO(carteiraAtualizada);
    }

    public SaldoDTO depositarDinheiro(Integer idCarteira, double valor) throws RegraDeNegocioException {
        buscarCarteiraPorId(idCarteira);
        log.info("Depositando R$ {} na carteira ID: {}", valor, idCarteira);
        CarteiraEntity carteira = carteiraRepository.buscarPorId(idCarteira);
        if (valor <= 0) {
            throw new RegraDeNegocioException("Valor de depósito inválido: R$ " + valor);
        }
        carteira.setDinheiro(carteira.getDinheiro() + valor);
        CarteiraEntity carteiraAtualizada = carteiraRepository.editar(idCarteira, carteira);
        log.info("Depósito de R$ {} realizado com sucesso na carteira ID {}. Saldo atual: R$ {}", valor, idCarteira, carteiraAtualizada.getDinheiro());

        emailService.sendEmailDepositarCarteira(carteira, valor);
        return objectMapper.convertValue(carteiraAtualizada, SaldoDTO.class);
    }

    public SaldoDTO sacarDinheiro(Integer idCarteira, double valor) throws RegraDeNegocioException {
        buscarCarteiraPorId(idCarteira);
        log.info("Sacando R$ {} da carteira ID: {}", valor, idCarteira);
        CarteiraEntity carteira = carteiraRepository.buscarPorId(idCarteira);
        if (valor <= 0) {
            throw new RegraDeNegocioException("Valor de saque inválido: R$ " + valor);
        }
        if (carteira.getDinheiro() < valor) {
            throw new RegraDeNegocioException("Saldo insuficiente para sacar R$ " + valor + " reais. Saldo atual: R$ " + carteira.getDinheiro());
        }
        carteira.setDinheiro(carteira.getDinheiro() - valor);
        CarteiraEntity carteiraAtualizada = carteiraRepository.editar(idCarteira, carteira);
        log.info("Saque de R$ {} realizado com sucesso da carteira ID {}. Saldo atual: R$ {}", valor, idCarteira, carteiraAtualizada.getDinheiro());

        emailService.sendEmailSacarCarteira(carteira, valor);
        return objectMapper.convertValue(carteiraAtualizada, SaldoDTO.class);
    }

    public SaldoDTO comprarFichas(Integer idCarteira, int quantidadeFicha) throws RegraDeNegocioException {
        buscarCarteiraPorId(idCarteira);
        log.info("Comprando {} fichas para a carteira ID: {}", quantidadeFicha, idCarteira);
        CarteiraEntity carteira = carteiraRepository.buscarPorId(idCarteira);
        final double valorFicha = 1.0;
        if (quantidadeFicha <= 0) {
            throw new RegraDeNegocioException("Quantidade de fichas para compra deve ser positiva: " + quantidadeFicha);
        }
        double custoTotal = quantidadeFicha * valorFicha;
        if (carteira.getDinheiro() < custoTotal) {
            throw new RegraDeNegocioException("Dinheiro insuficiente para comprar as fichas. Custo total: R$ " + custoTotal + ". Saldo atual: R$ " + carteira.getDinheiro());
        }
        carteira.setFichas(carteira.getFichas() + quantidadeFicha);
        carteira.setDinheiro(carteira.getDinheiro() - custoTotal);
        CarteiraEntity carteiraAtualizada = carteiraRepository.editar(idCarteira, carteira);
        log.info("Compra de {} fichas realizada com sucesso para a carteira ID {}. Fichas atuais: {}, Saldo atual: R$ {}", quantidadeFicha, idCarteira, carteiraAtualizada.getFichas(), carteiraAtualizada.getDinheiro());
        return objectMapper.convertValue(carteiraAtualizada, SaldoDTO.class);
    }

    public SaldoDTO venderFichas(Integer idCarteira, int quantidadeFicha) throws RegraDeNegocioException {
        buscarCarteiraPorId(idCarteira);
        log.info("Vendendo {} fichas da carteira ID: {}", quantidadeFicha, idCarteira);
        CarteiraEntity carteira = carteiraRepository.buscarPorId(idCarteira);
        final double valorFicha = 1.0;
        if (quantidadeFicha <= 0) {
            throw new RegraDeNegocioException("Quantidade de fichas para venda deve ser positiva: " + quantidadeFicha);
        }
        if (carteira.getFichas() < quantidadeFicha) {
            throw new RegraDeNegocioException("Quantidade de fichas insuficiente para vender. Fichas disponíveis: " + carteira.getFichas());
        }
        carteira.setFichas(carteira.getFichas() - quantidadeFicha);
        carteira.setDinheiro(carteira.getDinheiro() + (quantidadeFicha * valorFicha));
        CarteiraEntity carteiraAtualizada = carteiraRepository.editar(idCarteira, carteira);
        log.info("Venda de {} fichas realizada com sucesso da carteira ID {}. Fichas atuais: {}, Saldo atual: R$ {}", quantidadeFicha, idCarteira, carteiraAtualizada.getFichas(), carteiraAtualizada.getDinheiro());
        return objectMapper.convertValue(carteiraAtualizada, SaldoDTO.class);
    }
}