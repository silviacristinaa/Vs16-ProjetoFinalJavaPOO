package br.com.dbc.vemser.imperiodasfichas.repositories;

import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JogadorRepository implements GenericRepository<Integer, JogadorEntity> {
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
            con = ConexaoDataBase.getConnection();
            con.setAutoCommit(false); // Inicia a transação

            Integer proximoId = this.getProximoId(con);
            jogador.setIdJogador(proximoId);

            String sqlJogador = "INSERT INTO JOGADOR (ID, NOME, NICKNAME, IDADE) VALUES (?, ?, ?, ?)";

            PreparedStatement stmtJogador = con.prepareStatement(sqlJogador);

            stmtJogador.setInt(1, jogador.getIdJogador());
            stmtJogador.setString(2, jogador.getNome());
            stmtJogador.setString(3, jogador.getNickname());
            stmtJogador.setInt(4, jogador.getIdade());

            stmtJogador.executeUpdate();

            // Cria e associa a Carteira no banco de dados
            //Carteira carteira = jogador.getCarteira();
            //if (carteira == null) {
            //    carteira = new Carteira(); // Cria uma carteira padrão se não houver
            //    jogador.setCarteira(carteira);
            //}
            //carteira.setIdJogador(jogador.getIdJogador()); // Define o ID do jogador na carteira
            //carteira.criarCarteiraNoBanco(con); // Chama o método CRUD para criar no banco

            con.commit(); // Confirma a transação
            return jogador;
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
    public void remover(Integer id) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = ConexaoDataBase.getConnection();
            con.setAutoCommit(false); // Inicia a transação

            // Remove a carteira do jogador primeiro para manter a integridade referencial
            //if (jogador.getCarteira() != null && jogador.getCarteira().getIdCarteira() != null) {
            //    Carteira.removerCarteiraDoBanco(con, jogador.getCarteira().getIdCarteira());
            //}

            String sql = "DELETE FROM JOGADOR WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate();
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
    public JogadorEntity editar(Integer idJogador, JogadorEntity jogadorAtualizar) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = ConexaoDataBase.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE JOGADOR SET ");
            sql.append(" nome = ?,");
            sql.append(" nickname = ?,");
            sql.append(" idade = ? ");
            sql.append(" WHERE id = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, jogadorAtualizar.getNome());
            stmt.setString(2, jogadorAtualizar.getNickname());
            stmt.setInt(3, jogadorAtualizar.getIdade());
            stmt.setInt(4, idJogador);

            stmt.executeUpdate();

            jogadorAtualizar.setIdJogador(idJogador);
            return jogadorAtualizar; // Retorna o jogador atualizado (com a carteira também atualizada)
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
    public JogadorEntity buscarPorId(Integer id) throws RegraDeNegocioException {
        JogadorEntity jogador = null;
        Connection con = null;
        try {
            con = ConexaoDataBase.getConnection();

            String sql = "SELECT J.ID, J.NOME, J.NICKNAME, J.IDADE, C.ID AS CARTEIRA_ID, C.FICHAS, C.DINHEIRO " +
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

                // Verifica se há dados da carteira e a popula
//                if (res.getObject("CARTEIRA_ID") != null) {
//                    Carteira carteira = new Carteira(
//                            res.getInt("CARTEIRA_ID"),
//                            res.getInt("FICHAS"),
//                            res.getDouble("DINHEIRO"),
//                            res.getInt("ID") // ID do jogador
//                    );
//                    jogador.setCarteira(carteira);
//                } else {
//                    // Se o jogador não tiver carteira (o que não deveria acontecer com o design atual)
//                    jogador.setCarteira(new Carteira());
//                }
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
            con = ConexaoDataBase.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT J.ID, J.NOME, J.NICKNAME, J.IDADE, C.ID AS CARTEIRA_ID, C.FICHAS, C.DINHEIRO " +
                    "FROM JOGADOR J LEFT JOIN CARTEIRA C ON J.ID = C.ID_JOGADOR";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                JogadorEntity jogador = new JogadorEntity();
                jogador.setIdJogador(res.getInt("ID"));
                jogador.setNome(res.getString("NOME"));
                jogador.setNickname(res.getString("NICKNAME"));
                jogador.setIdade(res.getInt("IDADE"));

//                if (res.getObject("CARTEIRA_ID") != null) {
//                    Carteira carteira = new Carteira(
//                            res.getInt("CARTEIRA_ID"),
//                            res.getInt("FICHAS"),
//                            res.getDouble("DINHEIRO"),
//                            res.getInt("ID")
//                    );
//                    jogador.setCarteira(carteira);
//                } else {
//                    jogador.setCarteira(new Carteira());
//                }
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
