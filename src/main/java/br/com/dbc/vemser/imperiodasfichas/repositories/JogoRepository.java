package br.com.dbc.vemser.imperiodasfichas.repositories;

import br.com.dbc.vemser.imperiodasfichas.entities.JogoEntity;
import br.com.dbc.vemser.imperiodasfichas.enums.NomeJogoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface JogoRepository extends JpaRepository<JogoEntity, Integer> {
    JogoEntity findByNomeJogo(NomeJogoEnum nomeJogo);
}
