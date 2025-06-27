package br.com.dbc.vemser.imperiodasfichas.repositories;

import br.com.dbc.vemser.imperiodasfichas.database.ConexaoDataBase;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.JogoEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.PartidaEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PartidaRepository implements GenericRepository<Integer, PartidaEntity> {
    private final ConexaoDataBase conexaoDataBase;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_PARTIDA.nextval mysequence from DUAL";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public PartidaEntity adicionar(PartidaEntity partida) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();
            con.setAutoCommit(false);

            Integer proximoId = this.getProximoId(con);
            partida.setIdPartida(proximoId);

            String sql = "INSERT INTO PARTIDA\n" +
                    "(ID_PARTIDA, DATA_HORA, FICHAS_APOSTADAS, GANHOU, ID_JOGADOR, ID_JOGO)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, partida.getIdPartida());
            stmt.setTimestamp(2, Timestamp.valueOf(partida.getDataHora()));
            stmt.setInt(3, partida.getQuantidadeFichasApostado());
            stmt.setString(4, partida.isGanhou() ? "S" : "N");
            stmt.setInt(5, partida.getJogador().getIdJogador());
            stmt.setInt(6, partida.getJogo().getIdJogo());

            stmt.executeUpdate();
            con.commit();
            return partida;
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RegraDeNegocioException(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void remover(Integer id) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();
            con.setAutoCommit(false);

            String sql = "DELETE FROM PARTIDA WHERE ID_PARTIDA = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RegraDeNegocioException(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public PartidaEntity editar(Integer id, PartidaEntity partidaEntity) throws RegraDeNegocioException {
        return null;
    }

    @Override
    public PartidaEntity buscarPorId(Integer id) throws RegraDeNegocioException {
        PartidaEntity partida = null;
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();

            String sql = "SELECT * FROM PARTIDA p\n" +
                    "INNER JOIN JOGADOR j ON j.ID = p.ID_JOGADOR\n" +
                    "INNER JOIN JOGO jogo ON jogo.ID = p.ID_JOGO\n" +
                    "WHERE p.ID_PARTIDA = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                JogoEntity jogo = new JogoEntity(res.getInt("ID_JOGO"), res.getString("NOME_JOGO"),
                        res.getString("REGRAS"), res.getInt("VALOR_INICIAL"));

                JogadorEntity jogador = new JogadorEntity(res.getInt("ID_JOGADOR"), res.getString("NOME"),
                        res.getString("NICKNAME"), res.getInt("IDADE"));

                partida = new PartidaEntity(
                        res.getInt("ID_PARTIDA"),
                        res.getTimestamp("DATA_HORA").toLocalDateTime(),
                        res.getInt("FICHAS_APOSTADAS"),
                        "S".equalsIgnoreCase(res.getString("GANHOU")),
                        jogo,
                        jogador
                );
            }
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return partida;
    }

    @Override
    public List<PartidaEntity> listar() throws RegraDeNegocioException {
        List<PartidaEntity> partidas = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT ID_PARTIDA, DATA_HORA, FICHAS_APOSTADAS, GANHOU, ID_JOGADOR, ID_JOGO FROM PARTIDA";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                PartidaEntity partida = new PartidaEntity();
                partida.setIdPartida(res.getInt("ID_PARTIDA"));
                partida.setDataHora((res.getTimestamp("DATA_HORA").toLocalDateTime()));
                partida.setQuantidadeFichasApostado(res.getInt("FICHAS_APOSTADAS"));
                partida.setGanhou("S".equalsIgnoreCase(res.getString("GANHOU")));

                JogadorEntity jogador = new JogadorEntity();
                jogador.setIdJogador(res.getInt("ID_JOGADOR"));
                partida.setJogador(jogador);

                JogoEntity jogo = new JogoEntity();
                jogo.setIdJogo(res.getInt("ID_JOGO"));
                partida.setJogo(jogo);

                partidas.add(partida);
            }
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return partidas;
    }
}
