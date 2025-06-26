package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.repositories.JogadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final CarteiraService carteiraService;

    public JogadorEntity adicionarJogador(JogadorEntity jogador) throws RegraDeNegocioException {
//        if (jogadorExiste(nickname)) {
//            throw new DadosDuplicadosException("Jogador com o nickname " + nickname + " já existe.");
//        }
        JogadorEntity jogadorAdicionado = jogadorRepository.adicionar(jogador);
        CarteiraEntity carteira = new CarteiraEntity();
        carteira.setIdJogador(jogadorAdicionado.getIdJogador());

        CarteiraEntity carteiraAdicionada = carteiraService.adicionarCarteira(carteira);
        jogadorAdicionado.setCarteira(carteiraAdicionada);

        return jogadorAdicionado;
    }

//    public boolean jogadorExiste(String nicknameJogador) throws RegraDeNegocioException {
//        return jogadorRepository.buscar(nicknameJogador) != null;
//    }

    public List<JogadorEntity> listar() throws RegraDeNegocioException {
        return jogadorRepository.listar();
    }

    public JogadorEntity buscarJogadorPorId(Integer idJogador) throws Exception {
        JogadorEntity jogador = jogadorRepository.buscarPorId(idJogador);
        if (jogador == null) {
            throw new RegraDeNegocioException("Jogador não encontrado!");
        }
        return jogador;
    }

    public JogadorEntity atualizarJogador(Integer id, JogadorEntity jogadorAtualizar) throws Exception {
        buscarJogadorPorId(id);

        return jogadorRepository.editar(id, jogadorAtualizar);
    }

    public void removerJogador(Integer idJogador) throws Exception {
        buscarJogadorPorId(idJogador);

//        if (daoGenericoPartida != null) {
//            Partida partida = daoGenericoPartida.buscar(jogador.getIdJogador().toString());
//            if (partida != null) {
//                partida.setJogador(jogador);
//                daoGenericoPartida.remover(partida);
//            }
//        }
        jogadorRepository.remover(idJogador);
    }
}
