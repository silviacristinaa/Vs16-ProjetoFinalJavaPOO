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

    public void removerCarteira(Integer idCarteira) throws RegraDeNegocioException {
        buscarCarteiraPorId(idCarteira);
        carteiraRepository.remover(idCarteira);
    }
}