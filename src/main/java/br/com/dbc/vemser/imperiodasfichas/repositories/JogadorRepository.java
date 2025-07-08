package br.com.dbc.vemser.imperiodasfichas.repositories;
import br.com.dbc.vemser.imperiodasfichas.dtos.RelatorioJogadorSimplesDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorRankingDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // No JogadorRepository
    @Query("SELECT NEW br.com.dbc.vemser.imperiodasfichas.dtos.RelatorioJogadorSimplesDTO(" +
            "j.idJogador, j.nome, j.email, j.nickname, j.idade, " +
            "c.fichas, c.dinheiro) " +
            "FROM JOGADOR j " +
            "LEFT JOIN j.carteira c")
    List<RelatorioJogadorSimplesDTO> relatorioJogadoresSimples();

    @Query("SELECT NEW br.com.dbc.vemser.imperiodasfichas.dtos.RelatorioJogadorSimplesDTO(" +
            "j.idJogador, j.nome, j.email, j.nickname, j.idade, " +
            "c.fichas, c.dinheiro) " +
            "FROM JOGADOR j " +
            "LEFT JOIN j.carteira c")
    Page<RelatorioJogadorSimplesDTO> relatorioJogadoresSimplesPaginado(Pageable pageable);

    @Query("SELECT new br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorRankingDTO(" +
            "j.id, j.nickname, COUNT(p)) " +
            "FROM PartidaEntity p " +
            "JOIN p.jogador j " +
            "WHERE p.ganhou = 'S' " +
            "AND (:idJogador IS NULL OR j.id = :idJogador) " +
            "GROUP BY j.id, j.nickname " +
            "ORDER BY COUNT(p) DESC")
    List<JogadorRankingDTO> getRankingPorVitorias(@Param("idJogador") Integer idJogador);

    @Query("SELECT new br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorRankingDTO(" +
            "j.id, j.nickname, COUNT(p)) " +
            "FROM PartidaEntity p " +
            "JOIN p.jogador j " +
            "WHERE p.ganhou = 'S' " +
            "AND (:idJogador IS NULL OR j.id = :idJogador) " +
            "GROUP BY j.id, j.nickname " +
            "ORDER BY COUNT(p) DESC")
    Page<JogadorRankingDTO> getRankingPorVitoriasPaginado(@Param("idJogador") Integer idJogador, Pageable pageable);

}







