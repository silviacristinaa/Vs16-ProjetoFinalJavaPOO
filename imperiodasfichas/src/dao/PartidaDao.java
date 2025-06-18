package dao;

import dao.db.DataBaseSingleton;
import dao.interfaces.DaoGenerico;
import exceptions.RegraDeNegocioException;
import model.Jogador;
import model.Partida;

import java.sql.*;
import java.util.List;

public class PartidaDao implements DaoGenerico<Partida, String> {
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_partida.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Partida adicionar(Partida partida) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();

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
            stmt.setInt(6, 3);

            int res = stmt.executeUpdate();
            System.out.println("adicionarPartida.res=" + res);
            return partida;
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
    public boolean remover(Partida partida) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();

            String sql = "DELETE FROM PARTIDA WHERE id_jogador = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, partida.getJogador().getIdJogador());

            int res = stmt.executeUpdate();
            System.out.println("removerPartidaPorIdJogador.res=" + res);

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
    public Partida buscar(String id_jogador) throws RegraDeNegocioException {
        int id = Integer.parseInt(id_jogador);
        Partida partida = null;
        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();

            String sql = "SELECT * FROM PARTIDA WHERE id_jogador = ? AND ROWNUM = 1";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                partida = new Partida();
                partida.setIdPartida(res.getInt("id_partida"));
                partida.setDataHora(res.getTimestamp("data_hora").toLocalDateTime());
                partida.setQuantidadeFichasApostado(res.getInt("fichas_apostadas"));
                partida.setGanhou("S".equalsIgnoreCase(res.getString("ganhou")));
                Jogador jogador = new Jogador();
                jogador.setIdJogador(res.getInt("id_jogador"));
                partida.setJogador(jogador);
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
        return partida;
    }

    @Override
    public Partida atualizar(Partida entidade, String key) throws RegraDeNegocioException {
        return null;
    }

    @Override
    public List<Partida> listar() throws RegraDeNegocioException {
        return List.of();
    }
}
