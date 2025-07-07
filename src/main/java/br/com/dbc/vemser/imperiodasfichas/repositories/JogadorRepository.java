package br.com.dbc.vemser.imperiodasfichas.repositories;
import br.com.dbc.vemser.imperiodasfichas.dtos.RelatorioPartidasJogadorDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorRankingDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<JogadorEntity, Integer> {
    Optional<JogadorEntity> findByNickname(String nickname);
    Optional<JogadorEntity> findByEmail(String email);


//    @Query("SELECT new br.com.dbc.vemser.jogo.dto.RelatorioPartidasJogadorDTO(" +
//            "j.idJogador, j.nome, j.email, " +
//            "p.idPartida, p.dataHora, " +
//            "jg.nome, " +
//            "p.fichasGanhas, p.valorPremio) " +
//            "FROM Jogador j " +
//            "LEFT JOIN j.partidas p " +
//            "LEFT JOIN p.jogo jg " +
//            "WHERE (:idJogador IS NULL OR j.idJogador = :idJogador) " +
//            "ORDER BY p.dataHora DESC")
//    static List<RelatorioPartidasJogadorDTO> findPartidasPorJogador(@Param("idJogador") Long idJogador) {
//        return null;

    @Query("SELECT DISTINCT new br.com.dbc.vemser.jogo.dto.RelatorioJogadorPartidasDTO(" +
            "j.idJogador, j.nome, j.email, " +
            "p.idPartida, p.dataHora, " +
            "jg.nome, " +
            "p.fichasGanhas, p.valorPremio) " +
            "FROM Jogador j " +
            "INNER JOIN j.partidas p " +  // Troque LEFT JOIN por INNER JOIN
            "JOIN p.jogo jg " +
            "ORDER BY j.nome, p.dataHora DESC")
    List<RelatorioPartidasJogadorDTO> findJogadoresComPartidas();
}



