package model;

import dao.db.DataBaseSingleton;
import exceptions.RegraDeNegocioException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Carteira {
    private Integer idCarteira;
    private int fichas;
    private double dinheiro;
    private Integer idJogador;

    public Carteira() {
        this.fichas = 10;
        this.dinheiro = 0;
    }

    public Carteira(int fichas, double dinheiro) {
        this.fichas = fichas;
        this.dinheiro = dinheiro;
    }

    public Carteira(Integer idCarteira, int fichas, double dinheiro, Integer idJogador) {
        this.idCarteira = idCarteira;
        this.fichas = fichas;
        this.dinheiro = dinheiro;
        this.idJogador = idJogador;
    }

    public Integer getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(Integer idCarteira) {
        this.idCarteira = idCarteira;
    }

    public int getFichas() {
        return fichas;
    }

    public double getDinheiro() {
        return dinheiro;
    }

    public Integer getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Integer idJogador) {
        this.idJogador = idJogador;
    }

    public boolean depositarDinheiro(double valor) {
        if (valor <= 0) {
            return false;
        }
        this.dinheiro += valor;
        return true;
    }

    public boolean sacarDinheiro(double valor) {
        if (this.dinheiro >= valor) {
            this.dinheiro -= valor;
            return true;
        }
        return false;
    }

    public void depositarFichasCompradas(int quantidadeFicha, double valorFicha) {
        this.fichas += quantidadeFicha;
        this.dinheiro -= quantidadeFicha * valorFicha;
    }

    public boolean sacarFichasVendidas(int quantidadeFicha, double valorFicha) {
        if (this.fichas >= quantidadeFicha) {
            this.fichas -= quantidadeFicha;
            this.dinheiro += quantidadeFicha * valorFicha;
            return true;
        }
        return false;
    }

    public void adicionarFichas(int quantidade) {
        if (quantidade > 0) {
            this.fichas += quantidade;
        }
    }

    public void removerFichas(int quantidade) {
        if (quantidade > 0 && this.fichas >= quantidade) {
            this.fichas -= quantidade;
        }
    }

    // Operações CRUD

    // Create
    public void criarCarteiraNoBanco(Connection con) throws RegraDeNegocioException {
        try {
            Integer proximoId = getProximoId(con);
            this.setIdCarteira(proximoId);

            String sql = "INSERT INTO CARTEIRA (ID, FICHAS, DINHEIRO, ID_JOGADOR) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getIdCarteira());
            stmt.setInt(2, this.getFichas());
            stmt.setDouble(3, this.getDinheiro());
            stmt.setInt(4, this.getIdJogador());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
        }
    }

    // Read
    public static Carteira buscarCarteiraPorIdJogador(Connection con, Integer idJogador) throws RegraDeNegocioException {
        Carteira carteira = null;
        try {
            String sql = "SELECT ID, FICHAS, DINHEIRO, ID_JOGADOR FROM CARTEIRA WHERE ID_JOGADOR = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idJogador);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                carteira = new Carteira(
                        res.getInt("ID"),
                        res.getInt("FICHAS"),
                        res.getDouble("DINHEIRO"),
                        res.getInt("ID_JOGADOR")
                );
            }
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
        }
        return carteira;
    }

    // Update
    public void atualizarCarteiraNoBanco(Connection con) throws RegraDeNegocioException {
        try {
            String sql = "UPDATE CARTEIRA SET FICHAS = ?, DINHEIRO = ? WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, this.getFichas());
            stmt.setDouble(2, this.getDinheiro());
            stmt.setInt(3, this.getIdCarteira());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
        }
    }

    // Delete
    public static boolean removerCarteiraDoBanco(Connection con, Integer idCarteira) throws RegraDeNegocioException {
        try {
            String sql = "DELETE FROM CARTEIRA WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idCarteira);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
        }
    }

    // Helper para obter o próximo ID da sequence
    private Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_CARTEIRA.nextval mysequence from DUAL";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public String toString() {
        return ">> 🎟️ Fichas atuais: " + fichas + "\n>> 💵 Dinheiro disponível: R$ " + dinheiro;
    }
}