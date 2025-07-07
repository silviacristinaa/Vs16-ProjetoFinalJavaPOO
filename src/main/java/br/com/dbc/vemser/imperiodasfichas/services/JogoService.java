package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.JogoRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.JogoResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.JogoEntity;
import br.com.dbc.vemser.imperiodasfichas.enums.NomeJogoEnum;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.repositories.JogoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JogoService {
    private final JogoRepository jogoRepository;
    private final ObjectMapper objectMapper;

    public JogoResponseDTO create(JogoRequestDTO jogo) throws RegraDeNegocioException {
        JogoEntity jogoEntity = objectMapper.convertValue(jogo, JogoEntity.class);
        jogoEntity = jogoRepository.save(jogoEntity);
        return objectMapper.convertValue(jogoEntity, JogoResponseDTO.class);
    }

    public List<JogoResponseDTO> list() throws RegraDeNegocioException {
        List<JogoEntity> jogos = jogoRepository.findAll();
        return jogos.stream()
                .map(jogo -> objectMapper.convertValue(jogo, JogoResponseDTO.class))
                .toList();
    }

    public JogoResponseDTO update(Integer id, JogoRequestDTO jogo) throws RegraDeNegocioException {
        JogoEntity jogoEntity = getJogo(id);

        jogoEntity.setNomeJogo(jogo.getNomeJogo());
        jogoEntity.setRegras(jogo.getRegras());
        jogoEntity.setValorInicial(jogo.getValorInicial());

        jogoRepository.save(jogoEntity);

        return objectMapper.convertValue(jogoEntity, JogoResponseDTO.class);
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        JogoEntity jogo = getJogo(id);
        jogoRepository.delete(jogo);
    }

    public JogoResponseDTO findById(Integer id) throws RegraDeNegocioException {
        JogoEntity jogo = getJogo(id);
        return objectMapper.convertValue(jogo, JogoResponseDTO.class);
    }

    public JogoResponseDTO buscarPorNomeJogo(NomeJogoEnum nomeJogo) throws RegraDeNegocioException {
        JogoEntity jogo = jogoRepository.findByNomeJogo(nomeJogo);
        if (jogo == null) {
            throw new RegraDeNegocioException("Jogo com nome " + nomeJogo + " não encontrado.");
        }
        return objectMapper.convertValue(jogo, JogoResponseDTO.class);
    }


    public List<JogoResponseDTO> buscarPorNomeJogo(String nomeJogo) throws RegraDeNegocioException {
        String termoNormalizado = normalizar(nomeJogo);
        List<JogoEntity> jogos = jogoRepository.findAll();
        List<JogoEntity> jogosFiltrados = jogos.stream()
                .filter(jogo -> normalizar(jogo.getNomeJogo().getNome()).contains(termoNormalizado))
                .toList();

        return jogosFiltrados.stream()
                .map(jogo -> objectMapper.convertValue(jogo, JogoResponseDTO.class))
                .collect(Collectors.toList());
    }

    private String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase();
    }

    private JogoEntity getJogo(Integer id) throws RegraDeNegocioException {
        return jogoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Jogo com ID " + id + " não encontrado."));
    }
}
