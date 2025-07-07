package br.com.dbc.vemser.imperiodasfichas.repositories;
import br.com.dbc.vemser.imperiodasfichas.dtos.RelatorioJogadorSimplesDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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



}







