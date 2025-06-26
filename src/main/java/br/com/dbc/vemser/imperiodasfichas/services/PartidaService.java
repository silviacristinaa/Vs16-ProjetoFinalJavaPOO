package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaCreateDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.PartidaEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.repositories.PartidaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;

    private final JogadorService jogadorService;
    // jogo service

    private final ObjectMapper objectMapper;

    public PartidaDTO adicionarPartida(PartidaCreateDTO partida) throws Exception {
        // verificar se o jogo existe: partida.getIdJogo();
        JogadorEntity jogador = jogadorService.buscarJogadorPorId(partida.getIdJogador());

        //PartidaEntity partidaEntity = objectMapper.convertValue(partida, PartidaEntity.class);
        PartidaEntity partidaEntity = new PartidaEntity();
        partidaEntity.setDataHora(partida.getDataHora());
        partidaEntity.setQuantidadeFichasApostado(partida.getQuantidadeFichasApostado());
        partidaEntity.setGanhou(partida.isGanhou());
        //partidaEntity.setJogo(jogo);
        partidaEntity.setJogador(jogador);

        partidaEntity = partidaRepository.adicionar(partidaEntity);
        PartidaDTO partidaDTO = objectMapper.convertValue(partidaEntity, PartidaDTO.class);
        return partidaDTO;
    }

    public List<PartidaEntity> listar() throws RegraDeNegocioException {
        return partidaRepository.listar();
    }

    public PartidaEntity buscarPartidaPorId(Integer idPartida) throws Exception {
        PartidaEntity partida = partidaRepository.buscarPorId(idPartida);
        if (partida == null) {
            throw new RegraDeNegocioException("Partida não encontrada!");
        }
        return partida;
    }

    public void removerPartida(Integer idPartida) throws Exception {
        buscarPartidaPorId(idPartida);

        partidaRepository.remover(idPartida);
    }

    // implementar editarPartida
}
