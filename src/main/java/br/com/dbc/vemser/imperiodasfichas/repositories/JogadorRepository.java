package br.com.dbc.vemser.imperiodasfichas.repositories;

import br.com.dbc.vemser.imperiodasfichas.database.ConexaoDataBase;
import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class JogadorRepository implements GenericRepository<Integer, JogadorEntity> {
    private final CarteiraRepository carteiraRepository;
    private final ConexaoDataBase conexaoDataBase;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_JOGADOR.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public JogadorEntity adicionar(JogadorEntity jogador) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();
            con.setAutoCommit(false);

            Integer proximoId = this.getProximoId(con);
            jogador.setIdJogador(proximoId);

            String sqlJogador = "INSERT INTO JOGADOR (ID, NOME, NICKNAME, IDADE, EMAIL) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement stmtJogador = con.prepareStatement(sqlJogador);

            stmtJogador.setInt(1, jogador.getIdJogador());
            stmtJogador.setString(2, jogador.getNome());
            stmtJogador.setString(3, jogador.getNickname());
            stmtJogador.setInt(4, jogador.getIdade());
            stmtJogador.setString(5, jogador.getEmail());

            stmtJogador.executeUpdate();

            con.commit();
            return jogador;
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

            CarteiraEntity carteiraJogador = carteiraRepository.buscarCarteiraPorIdJogador(id);
            if (carteiraJogador != null) {
                carteiraRepository.remover(carteiraJogador.getIdCarteira());
            }

            String sql = "DELETE FROM JOGADOR WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();
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
    public JogadorEntity editar(Integer idJogador, JogadorEntity jogadorAtualizar) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE JOGADOR SET ");
            sql.append(" nome = ?,");
            sql.append(" nickname = ?,");
            sql.append(" idade = ? ");
            sql.append(" email = ? ");
            sql.append(" WHERE id = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, jogadorAtualizar.getNome());
            stmt.setString(2, jogadorAtualizar.getNickname());
            stmt.setInt(3, jogadorAtualizar.getIdade());
            stmt.setString(4, jogadorAtualizar.getEmail());
            stmt.setInt(5, idJogador);

            stmt.executeUpdate();

            jogadorAtualizar.setIdJogador(idJogador);
            return jogadorAtualizar; // Retorna o jogador atualizado (com a carteira também atualizada)
        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
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
    public JogadorEntity buscarPorId(Integer id) throws RegraDeNegocioException {
        JogadorEntity jogador = null;
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();

            String sql = "SELECT J.ID, J.NOME, J.NICKNAME, J.IDADE, J.EMAIL, C.ID AS CARTEIRA_ID, C.FICHAS, C" +
                    ".DINHEIRO" +
                    " " +
                    "FROM JOGADOR J LEFT JOIN CARTEIRA C ON J.ID = C.ID_JOGADOR " +
                    "WHERE J.ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                jogador = new JogadorEntity();
                jogador.setIdJogador(res.getInt("ID"));
                jogador.setNome(res.getString("NOME"));
                jogador.setNickname(res.getString("NICKNAME"));
                jogador.setIdade(res.getInt("IDADE"));
                jogador.setEmail(res.getString("EMAIL"));

                CarteiraEntity carteira = new CarteiraEntity();
                carteira.setIdCarteira(res.getInt("CARTEIRA_ID"));
                jogador.setCarteira(carteira);
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
        return jogador;
    }

    public JogadorEntity buscarPorNickname(String nickname) throws RegraDeNegocioException {
        JogadorEntity jogador = null;
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();

            String sql = "SELECT J.ID, J.NOME, J.NICKNAME, J.IDADE, J.EMAIL, C.ID AS CARTEIRA_ID, C.FICHAS, C" +
                    ".DINHEIRO" +
                    " " +
                    "FROM JOGADOR J LEFT JOIN CARTEIRA C ON J.ID = C.ID_JOGADOR " +
                    "WHERE J.NICKNAME = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nickname);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                jogador = new JogadorEntity();
                jogador.setIdJogador(res.getInt("ID"));
                jogador.setNome(res.getString("NOME"));
                jogador.setNickname(res.getString("NICKNAME"));
                jogador.setIdade(res.getInt("IDADE"));
                jogador.setEmail(res.getString("EMAIL"));

                CarteiraEntity carteira = new CarteiraEntity();
                carteira.setIdCarteira(res.getInt("CARTEIRA_ID"));
                jogador.setCarteira(carteira);
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
        return jogador;
    }

    public JogadorEntity buscarPorEmail(String email) throws RegraDeNegocioException {
        JogadorEntity jogador = null;
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();

            String sql = "SELECT J.ID, J.NOME, J.NICKNAME, J.IDADE, J.EMAIL, C.ID AS CARTEIRA_ID, C.FICHAS, C" +
                    ".DINHEIRO" +
                    " " +
                    "FROM JOGADOR J LEFT JOIN CARTEIRA C ON J.ID = C.ID_JOGADOR " +
                    "WHERE J.EMAIL = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                jogador = new JogadorEntity();
                jogador.setIdJogador(res.getInt("ID"));
                jogador.setNome(res.getString("NOME"));
                jogador.setNickname(res.getString("NICKNAME"));
                jogador.setIdade(res.getInt("IDADE"));
                jogador.setEmail(res.getString("EMAIL"));

                CarteiraEntity carteira = new CarteiraEntity();
                carteira.setIdCarteira(res.getInt("CARTEIRA_ID"));
                jogador.setCarteira(carteira);
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
        return jogador;
    }

    @Override
    public List<JogadorEntity> listar() throws RegraDeNegocioException {
        List<JogadorEntity> jogadores = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT J.ID, J.NOME, J.NICKNAME, J.IDADE, J.EMAIL, C.ID AS CARTEIRA_ID, C.FICHAS, C" +
                    ".DINHEIRO " +
                    "FROM JOGADOR J LEFT JOIN CARTEIRA C ON J.ID = C.ID_JOGADOR";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                JogadorEntity jogador = new JogadorEntity();
                jogador.setIdJogador(res.getInt("ID"));
                jogador.setNome(res.getString("NOME"));
                jogador.setNickname(res.getString("NICKNAME"));
                jogador.setIdade(res.getInt("IDADE"));
                jogador.setEmail(res.getString("EMAIL"));

                CarteiraEntity carteira = new CarteiraEntity();
                carteira.setIdCarteira(res.getInt("CARTEIRA_ID"));
                jogador.setCarteira(carteira);

                jogadores.add(jogador);
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
        return jogadores;
    }
}
