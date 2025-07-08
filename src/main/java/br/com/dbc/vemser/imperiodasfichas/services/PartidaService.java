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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PartidaService {
    private final PartidaRepository partidaRepository;
    private final ObjectMapper objectMapper;

    public PartidaResponseDTO adicionarPartida(PartidaRequestDTO partida) throws RegraDeNegocioException {
        PartidaEntity partidaEntity = objectMapper.convertValue(partida, PartidaEntity.class);
        partidaEntity.setFichasApostadas(partida.getQuantidadeFichasApostado());

        partidaEntity.setJogo(new JogoEntity(partida.getIdJogo()));
        partidaEntity.setJogador(new JogadorEntity(partida.getIdJogador()));
        partidaEntity.setGanhou(partida.getGanhou() ? "S" : "N");

        partidaEntity = partidaRepository.save(partidaEntity);

        return converterEntityParaResponse(partidaEntity);
    }

    public PartidaResponseDTO buscarPartidaPorId(Integer idPartida) throws RegraDeNegocioException {
        PartidaEntity partida = partidaRepository.findById(idPartida)
                .orElseThrow(() -> new RegraDeNegocioException("Partida com id: " + idPartida + " não encontrada!"));

        return converterEntityParaResponse(partida);
    }

    public List<PartidaResponseDTO> listar() throws RegraDeNegocioException {
        return partidaRepository.findAll()
                .stream()
                .map(this::converterEntityParaResponse)
                .collect(Collectors.toList());
    }

    private PartidaResponseDTO converterEntityParaResponse(PartidaEntity partidaEntity) {
        PartidaResponseDTO dto = new PartidaResponseDTO();
        dto.setIdPartida(partidaEntity.getIdPartida());
        dto.setDataHora(partidaEntity.getDataHora()); // agora é LocalDateTime, sem conversão
        dto.setQuantidadeFichasApostado(partidaEntity.getFichasApostadas());
        dto.setGanhou("S".equals(partidaEntity.getGanhou()));
        dto.setIdJogo(partidaEntity.getJogo().getIdJogo());
        dto.setIdJogador(partidaEntity.getJogador().getIdJogador());
        return dto;
    }


    public void removerPartida(Integer idPartida) throws RegraDeNegocioException {
        PartidaEntity partida = partidaRepository.findById(idPartida)
                .orElseThrow(() -> new RegraDeNegocioException("Partida com id: " + idPartida + " não encontrada!"));
        partidaRepository.delete(partida);
    }

    public Page<PartidaResponseDTO> listarPaginado(int pagina, int tamanhoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        Page<PartidaEntity> paginaPartidas = partidaRepository.findAll(pageable);

        return paginaPartidas.map(this::converterEntityParaResponse);
    }

}