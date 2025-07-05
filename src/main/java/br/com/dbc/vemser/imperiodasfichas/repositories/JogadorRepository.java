package br.com.dbc.vemser.imperiodasfichas.repositories;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorRankingDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<JogadorEntity, Integer> {
    Optional<JogadorEntity> findByNickname(String nickname);
    Optional<JogadorEntity> findByEmail(String email);

}
