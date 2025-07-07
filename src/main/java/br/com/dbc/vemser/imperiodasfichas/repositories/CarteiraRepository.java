package br.com.dbc.vemser.imperiodasfichas.repositories;

import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarteiraRepository extends JpaRepository<CarteiraEntity, Integer> {

    Optional<CarteiraEntity> findByJogadorIdJogador(Integer idJogador);
}
