package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.JogoResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaCreateDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.JogoEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.PartidaEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.repositories.PartidaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PartidaService {
    private final PartidaRepository partidaRepository;
    private final JogadorService jogadorService;
    private final JogoService jogoService;
    private final ObjectMapper objectMapper;

    public PartidaDTO adicionarPartida(PartidaCreateDTO partida) throws Exception {
        JogoResponseDTO jogoDTO = jogoService.findById(partida.getIdJogo());
        JogadorEntity jogador = jogadorService.buscarJogadorPorId(partida.getIdJogador());

        JogoEntity jogo = objectMapper.convertValue(jogoDTO, JogoEntity.class);

        PartidaEntity partidaEntity = objectMapper.convertValue(partida, PartidaEntity.class);
        partidaEntity.setJogo(jogo);
        partidaEntity.setJogador(jogador);

        partidaEntity = partidaRepository.adicionar(partidaEntity);

        PartidaDTO partidaDTO = objectMapper.convertValue(partidaEntity, PartidaDTO.class);
        partidaDTO.setIdJogo(jogo.getIdJogo());
        partidaDTO.setIdJogador(jogador.getIdJogador());

        return partidaDTO;
    }

    public List<PartidaDTO> listar() throws RegraDeNegocioException {
        List<PartidaEntity> partidas = partidaRepository.listar();

        List<PartidaDTO> partidaDTOS = partidas.stream()
                .map(partida -> {
                    PartidaDTO partidaDTO = objectMapper.convertValue(partida, PartidaDTO.class);
                    partidaDTO.setIdJogo(partida.getJogo().getIdJogo());
                    partidaDTO.setIdJogador(partida.getJogador().getIdJogador());

                    return partidaDTO;
                })
                .collect(Collectors.toList());

        return partidaDTOS;
    }

    public PartidaDTO buscarPartidaPorId(Integer idPartida) throws Exception {
        PartidaEntity partida = partidaRepository.buscarPorId(idPartida);
        if (partida == null) {
            throw new RegraDeNegocioException("Partida não encontrada!");
        }

        PartidaDTO partidaDTO = objectMapper.convertValue(partida, PartidaDTO.class);
        partidaDTO.setIdJogo(partida.getJogo().getIdJogo());
        partidaDTO.setIdJogador(partida.getJogador().getIdJogador());

        return partidaDTO;
    }

    public void removerPartida(Integer idPartida) throws Exception {
        buscarPartidaPorId(idPartida);
        partidaRepository.remover(idPartida);
    }
}
