package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaResponseDTO;
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
    private final ObjectMapper objectMapper;

    public PartidaResponseDTO adicionarPartida(PartidaRequestDTO partida) throws Exception {
        PartidaEntity partidaEntity = objectMapper.convertValue(partida, PartidaEntity.class);

        partidaEntity.setJogo(new JogoEntity(partida.getIdJogo()));
        partidaEntity.setJogador(new JogadorEntity(partida.getIdJogador()));

        partidaEntity = partidaRepository.adicionar(partidaEntity);

        PartidaResponseDTO partidaDTO = objectMapper.convertValue(partidaEntity, PartidaResponseDTO.class);
        partidaDTO.setIdJogo(partida.getIdJogo());
        partidaDTO.setIdJogador(partida.getIdJogador());

        return partidaDTO;
    }

    public List<PartidaResponseDTO> listar() throws RegraDeNegocioException {
        List<PartidaEntity> partidas = partidaRepository.listar();

        List<PartidaResponseDTO> partidaDTOS = partidas.stream()
                .map(partida -> {
                    PartidaResponseDTO partidaDTO = objectMapper.convertValue(partida, PartidaResponseDTO.class);
                    partidaDTO.setIdJogo(partida.getJogo().getIdJogo());
                    partidaDTO.setIdJogador(partida.getJogador().getIdJogador());

                    return partidaDTO;
                })
                .collect(Collectors.toList());

        return partidaDTOS;
    }

    public PartidaResponseDTO buscarPartidaPorId(Integer idPartida) throws Exception {
        PartidaEntity partida = partidaRepository.buscarPorId(idPartida);
        if (partida == null) {
            throw new RegraDeNegocioException("Partida não encontrada!");
        }

        PartidaResponseDTO partidaDTO = objectMapper.convertValue(partida, PartidaResponseDTO.class);
        partidaDTO.setIdJogo(partida.getJogo().getIdJogo());
        partidaDTO.setIdJogador(partida.getJogador().getIdJogador());

        return partidaDTO;
    }

    public void removerPartida(Integer idPartida) throws Exception {
        buscarPartidaPorId(idPartida);
        partidaRepository.remover(idPartida);
    }

    public void removerPartidasPorIdJogador(Integer idJogador) throws RegraDeNegocioException {
        partidaRepository.removerPartidasPorIdJogador(idJogador);
    }
}
