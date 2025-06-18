package dao;

import dao.db.DataBaseSingleton;
import dao.interfaces.DaoGenerico;
import exceptions.RegraDeNegocioException;
import model.jogos.Jogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogoDao implements DaoGenerico<Jogo, String> {

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT seq_jogo.nextval mysequence FROM DUAL";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public Jogo adicionar(Jogo jogo) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();
            Integer proximoId = this.getProximoId(con);

            String sql = "INSERT INTO JOGO (ID, NOME_JOGO, REGRAS, VALOR_INICIAL) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, proximoId);
            stmt.setString(2, jogo.getNomeJogo());
            stmt.setString(3, jogo.getRegras());
            stmt.setInt(4, jogo.getValorInicial());

            int res = stmt.executeUpdate();
            System.out.println("adicionarJogo.res=" + res);
            return jogo;
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean remover(Jogo jogo) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();
            String sql = "DELETE FROM JOGO WHERE NOME_JOGO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, jogo.getNomeJogo());
            int res = stmt.executeUpdate();
            System.out.println("removerJogo.res=" + res);
            return res > 0;
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Jogo buscar(String nomeDoJogo) throws RegraDeNegocioException {
        Jogo jogo = null;
        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();
            String sql = "SELECT * FROM JOGO WHERE NOME_JOGO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nomeDoJogo);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                jogo = new Jogo(res.getString("nome_jogo"), res.getString("regras"), res.getInt("valor_inicial")) {
                    @Override
                    public model.Partida jogar(model.Jogador jogador, int valorApostado, int opcaoEscolhida) { return null; }
                    @Override
                    public void apostaValida(int valorApostado, int opcaoEscolhida) {}
                    @Override
                    public void validarOpcao(int opcaoEscolhida) {}
                    @Override
                    public boolean verificarResultado(int resultado, int opcaoEscolhida) { return false; }
                    @Override
                    public void exibirResultado(model.Partida partida, int resultado) {}
                };
            }
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return jogo;
    }

    @Override
    public Jogo atualizar(Jogo jogo, String nomeDoJogo) throws RegraDeNegocioException {
        Jogo jogoExistente = buscar(nomeDoJogo);
        if (jogoExistente != null) {
            Connection con = null;
            try {
                con = DataBaseSingleton.getConnection();
                String sql = "UPDATE JOGO SET REGRAS = ?, VALOR_INICIAL = ? WHERE NOME_JOGO = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, jogo.getRegras());
                stmt.setInt(2, jogo.getValorInicial());
                stmt.setString(3, nomeDoJogo);
                int res = stmt.executeUpdate();
                System.out.println("atualizarJogo.res=" + res);
                return buscar(nomeDoJogo);
            } catch (SQLException e) {
                throw new RegraDeNegocioException(e.getCause());
            } finally {
                try {
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return jogoExistente;
    }

    @Override
    public List<Jogo> listar() throws RegraDeNegocioException {
        List<Jogo> jogos = new ArrayList<>();
        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();
            String sql = "SELECT * FROM JOGO";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                Jogo jogo = new Jogo(res.getString("nome_jogo"), res.getString("regras"), res.getInt("valor_inicial")) {
                    @Override
                    public model.Partida jogar(model.Jogador jogador, int valorApostado, int opcaoEscolhida) { return null; }
                    @Override
                    public void apostaValida(int valorApostado, int opcaoEscolhida) {}
                    @Override
                    public void validarOpcao(int opcaoEscolhida) {}
                    @Override
                    public boolean verificarResultado(int resultado, int opcaoEscolhida) { return false; }
                    @Override
                    public void exibirResultado(model.Partida partida, int resultado) {}
                };
                jogos.add(jogo);
            }
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return jogos;
    }
}