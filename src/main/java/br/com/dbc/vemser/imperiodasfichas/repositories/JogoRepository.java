package br.com.dbc.vemser.imperiodasfichas.repositories;

import br.com.dbc.vemser.imperiodasfichas.database.ConexaoDataBase;
import br.com.dbc.vemser.imperiodasfichas.entities.JogoEntity;
import br.com.dbc.vemser.imperiodasfichas.enums.NomeJogoEnum;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class JogoRepository implements GenericRepository<Integer, JogoEntity>{
    private final ConexaoDataBase conexaoDataBase;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_JOGO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public JogoEntity adicionar(JogoEntity jogo) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();
            con.setAutoCommit(false); // Inicia a transação

            Integer proximoId = this.getProximoId(con);
            jogo.setIdJogo(proximoId);

            String sqlJogo = "INSERT INTO JOGO (ID, NOME_JOGO, REGRAS, VALOR_INICIAL) VALUES (?, ?, ?, ?)";

            PreparedStatement stmtJogo = con.prepareStatement(sqlJogo);

            stmtJogo.setInt(1, jogo.getIdJogo());
            stmtJogo.setString(2, jogo.getNomeJogo().getNome());
            stmtJogo.setString(3, jogo.getRegras());
            stmtJogo.setInt(4, jogo.getValorInicial());

            stmtJogo.executeUpdate();

            con.commit(); // Confirma a transação
            return jogo;
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback(); // Desfaz a transação em caso de erro
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
    public JogoEntity editar(Integer id, JogoEntity jogoAtualizar) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE JOGO SET ");
            sql.append(" nome_jogo = ?,");
            sql.append(" regras = ?,");
            sql.append(" valor_inicial = ? ");
            sql.append(" WHERE id = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, jogoAtualizar.getNomeJogo().getNome());
            stmt.setString(2, jogoAtualizar.getRegras());
            stmt.setInt(3, jogoAtualizar.getValorInicial());
            stmt.setInt(4, id);

            stmt.executeUpdate();

            jogoAtualizar.setIdJogo(id);
            return jogoAtualizar; // Retorna o jogo atualizado (com a carteira também atualizada)
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback(); // Desfaz a transação em caso de erro
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
            con.setAutoCommit(false); // Inicia a transação

            String sql = "DELETE FROM JOGO WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();
            con.commit(); // Confirma a transação

        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback(); // Desfaz a transação em caso de erro
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
    public JogoEntity buscarPorId(Integer id) throws RegraDeNegocioException {
        JogoEntity jogo = null;
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();

            String sql = "SELECT ID, NOME_JOGO, REGRAS, VALOR_INICIAL FROM JOGO WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                jogo = new JogoEntity();
                jogo.setIdJogo(res.getInt("ID"));
                jogo.setNomeJogo(NomeJogoEnum.fromNome(res.getString("NOME_JOGO")));
                jogo.setRegras(res.getString("REGRAS"));
                jogo.setValorInicial(res.getInt("VALOR_INICIAL"));
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
        return jogo;
    }

    public JogoEntity buscarPorNomeJogo(NomeJogoEnum nomeJogo) throws RegraDeNegocioException {
        JogoEntity jogo = null;
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();

            String sql = "SELECT ID, NOME_JOGO, REGRAS, VALOR_INICIAL FROM JOGO WHERE NOME_JOGO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nomeJogo.getNome());
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                jogo = new JogoEntity();
                jogo.setIdJogo(res.getInt("ID"));
                jogo.setNomeJogo(NomeJogoEnum.fromNome(res.getString("NOME_JOGO")));
                jogo.setRegras(res.getString("REGRAS"));
                jogo.setValorInicial(res.getInt("VALOR_INICIAL"));
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
        return jogo;
    }

    @Override
    public List<JogoEntity> listar() throws RegraDeNegocioException {
        List<JogoEntity> jogos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();

            String sql = "SELECT ID, NOME_JOGO, REGRAS, VALOR_INICIAL FROM JOGO";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                JogoEntity jogo = new JogoEntity();
                jogo.setIdJogo(res.getInt("ID"));
                jogo.setNomeJogo(NomeJogoEnum.fromNome(res.getString("NOME_JOGO")));
                jogo.setRegras(res.getString("REGRAS"));
                jogo.setValorInicial(res.getInt("VALOR_INICIAL"));
                jogos.add(jogo);
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
        return jogos;
    }
}
