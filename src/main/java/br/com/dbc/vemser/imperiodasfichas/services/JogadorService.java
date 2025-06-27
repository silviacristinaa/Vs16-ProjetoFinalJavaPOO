package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.CarteiraResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.repositories.JogadorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final CarteiraService carteiraService;
    private final ObjectMapper objectMapper;

    public JogadorEntity adicionarJogador(JogadorEntity jogador) throws RegraDeNegocioException {
//        if (jogadorExiste(nickname)) {
//            throw new DadosDuplicadosException("Jogador com o nickname " + nickname + " já existe.");
//        }
        JogadorEntity jogadorAdicionado = jogadorRepository.adicionar(jogador);
        log.info("Jogador adicionado com sucesso! ID: {}", jogadorAdicionado.getIdJogador());

        CarteiraResponseDTO carteiraCriadaResponse = carteiraService.adicionarCarteira(jogadorAdicionado.getIdJogador());

        CarteiraEntity carteiraCompleta = objectMapper.convertValue(carteiraCriadaResponse, CarteiraEntity.class);

        jogadorAdicionado.setCarteira(carteiraCompleta);
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
