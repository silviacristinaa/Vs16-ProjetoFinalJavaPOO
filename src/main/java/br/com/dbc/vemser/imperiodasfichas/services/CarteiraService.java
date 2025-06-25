package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.repositories.CarteiraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarteiraService {

    private final CarteiraRepository carteiraRepository;

    public CarteiraService(CarteiraRepository carteiraRepository) {
        this.carteiraRepository = carteiraRepository;
    }

    public CarteiraEntity adicionarCarteira(CarteiraEntity carteira) throws RegraDeNegocioException {
        return carteiraRepository.adicionar(carteira);
    }

    public List<CarteiraEntity> listar() throws RegraDeNegocioException {
        return carteiraRepository.listar();
    }

    public CarteiraEntity buscarCarteiraPorId(Integer idCarteira) throws RegraDeNegocioException {
        return carteiraRepository.buscarPorId(idCarteira);
    }

    public CarteiraEntity buscarCarteiraPorIdJogador(Integer idJogador) throws RegraDeNegocioException {
        return carteiraRepository.buscarCarteiraPorIdJogador(idJogador);
    }

    public CarteiraEntity atualizarCarteira(Integer id, CarteiraEntity carteiraAtualizar) throws RegraDeNegocioException {
        buscarCarteiraPorId(id);
        return carteiraRepository.editar(id, carteiraAtualizar);
    }

    public CarteiraEntity depositarDinheiro(Integer idCarteira, double valor) throws RegraDeNegocioException {
        CarteiraEntity carteira = buscarCarteiraPorId(idCarteira);
        if (valor <= 0) {
            throw new RegraDeNegocioException("Valor de depósito inválido: R$ " + valor);
        }
        carteira.setDinheiro(carteira.getDinheiro() + valor);
        return carteiraRepository.editar(idCarteira, carteira);
    }

    public CarteiraEntity sacarDinheiro(Integer idCarteira, double valor) throws RegraDeNegocioException {
        CarteiraEntity carteira = buscarCarteiraPorId(idCarteira);
        if (valor <= 0) {
            throw new RegraDeNegocioException("Valor de saque inválido: R$ " + valor);
        }
        if (carteira.getDinheiro() < valor) {
            throw new RegraDeNegocioException("Saldo insuficiente para sacar R$ " + valor + "reais");
        }
        carteira.setDinheiro(carteira.getDinheiro() - valor);
        return carteiraRepository.editar(idCarteira, carteira);
    }

    public CarteiraEntity comprarFichas(Integer idCarteira, int quantidadeFicha) throws RegraDeNegocioException {
        CarteiraEntity carteira = buscarCarteiraPorId(idCarteira);
        final double valorFicha = 1.0;
        if (quantidadeFicha <= 0) {
            throw new RegraDeNegocioException("Quantidade de fichas para compra deve ser positiva: " + quantidadeFicha);
        }
        double custoTotal = quantidadeFicha * valorFicha;
        if (carteira.getDinheiro() < custoTotal) {
            throw new RegraDeNegocioException("Dinheiro insuficiente para comprar as fichas. Custo total: R$ " + custoTotal);
        }
        carteira.setFichas(carteira.getFichas() + quantidadeFicha);
        carteira.setDinheiro(carteira.getDinheiro() - custoTotal);
        return carteiraRepository.editar(idCarteira, carteira);
    }

    public CarteiraEntity venderFichas(Integer idCarteira, int quantidadeFicha) throws RegraDeNegocioException {
        CarteiraEntity carteira = buscarCarteiraPorId(idCarteira);
        final double valorFicha = 1.0;
        if (quantidadeFicha <= 0) {
            throw new RegraDeNegocioException("Quantidade de fichas para venda deve ser positiva: " + quantidadeFicha);
        }
        if (carteira.getFichas() < quantidadeFicha) {
            throw new RegraDeNegocioException("Quantidade de fichas insuficiente para vender.");
        }
        carteira.setFichas(carteira.getFichas() - quantidadeFicha);
        carteira.setDinheiro(carteira.getDinheiro() + (quantidadeFicha * valorFicha));
        return carteiraRepository.editar(idCarteira, carteira);
    }
}