package br.com.dbc.vemser.imperiodasfichas.services;
import br.com.dbc.vemser.imperiodasfichas.entities.JogoEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.repositories.JogoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JogoService {
    private final JogoRepository jogoRepository;

    public JogoEntity create(JogoEntity jogo) throws RegraDeNegocioException {
        return jogoRepository.adicionar(jogo);
    }

    public List<JogoEntity> list() throws RegraDeNegocioException {
        return jogoRepository.listar();
    }

    public JogoEntity update(Integer id, JogoEntity jogoAtualizar) throws RegraDeNegocioException {
        return jogoRepository.editar(id, jogoAtualizar);
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        jogoRepository.remover(id);
    }

    public JogoEntity findById(Integer id) throws RegraDeNegocioException {
        return jogoRepository.buscarPorId(id);
    }

    private JogoEntity getJogo(Integer id) throws RegraDeNegocioException {
        return jogoRepository.listar().stream()
                .filter(jogo -> jogo.getIdJogo().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Jogo não encontrado!"));
    }
}
