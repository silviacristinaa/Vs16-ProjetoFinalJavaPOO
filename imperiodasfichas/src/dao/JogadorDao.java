package dao;

import dao.db.DataBaseSingleton;
import dao.interfaces.DaoGenerico;
import exceptions.RegraDeNegocioException;
import model.Jogador;
import model.Carteira;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDao implements DaoGenerico<Jogador, String> {

    private DataBaseSingleton dataBase = DataBaseSingleton.getInstance();

    @Override
    public Jogador adicionar(Jogador jogador) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();

            Integer proximoId = this.getProximoId(con);
            jogador.setIdJogador(proximoId);

            String sql = "INSERT INTO JOGADOR\n" +
                    "(ID, NOME, NICKNAME, IDADE)\n" +
                    "VALUES(?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, jogador.getIdJogador());
            stmt.setString(2, jogador.getNome());
            stmt.setString(3, jogador.getNickname());
            stmt.setInt(4, jogador.getIdade());

            int res = stmt.executeUpdate();
            System.out.println("adicionarJogador.res=" + res);
            return jogador;
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
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
    public boolean remover(Jogador jogador) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();

            String sql = "DELETE FROM JOGADOR WHERE nickname = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, jogador.getNickname());

            int res = stmt.executeUpdate();
            System.out.println("removerJogadorPorNickname.res=" + res);

            return res > 0;
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
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
    public Jogador buscar(String nicknameJogador) throws RegraDeNegocioException {
        Jogador jogador = null;
        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();

            String sql = "SELECT * FROM JOGADOR WHERE nickname = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, nicknameJogador);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                jogador = new Jogador();
                jogador.setIdJogador(res.getInt("id"));
                jogador.setNome(res.getString("nome"));
                jogador.setNickname(res.getString("nickname"));
                jogador.setIdade(res.getInt("idade"));
                jogador.setCarteira(new Carteira()); // Instancia a carteira para evitar NullPointerException
            }
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
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
    public Jogador atualizar(Jogador jogador, String nicknameJogador) throws RegraDeNegocioException {

        Jogador jogadorExistente = buscar(nicknameJogador);
        if (jogadorExistente != null) {
            Connection con = null;
            try {
                con = DataBaseSingleton.getConnection();

                StringBuilder sql = new StringBuilder();
                sql.append("UPDATE JOGADOR SET ");
                sql.append(" nome = ?,");
                sql.append(" idade = ? ");
                sql.append(" WHERE nickname = ? ");

                PreparedStatement stmt = con.prepareStatement(sql.toString());

                stmt.setString(1, jogador.getNome());
                stmt.setInt(2, jogador.getIdade());
                stmt.setString(3, nicknameJogador);

                int res = stmt.executeUpdate();
                System.out.println("editarJogador.res=" + res);

                return jogadorExistente;
            } catch (SQLException e) {
                throw new RegraDeNegocioException(e.getCause());
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

        return jogadorExistente;
    }

    @Override
    public List<Jogador> listar() throws RegraDeNegocioException {
        List<Jogador> jogadores = new ArrayList<>();
        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM JOGADOR";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Jogador jogador = new Jogador();
                jogador.setIdJogador(res.getInt("id"));
                jogador.setNome(res.getString("nome"));
                jogador.setNickname(res.getString("nickname"));
                jogador.setIdade(res.getInt("idade"));
                jogador.setCarteira(new Carteira()); // Instancia a carteira para cada jogador listado
                jogadores.add(jogador);
            }
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
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

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_jogador.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }
}