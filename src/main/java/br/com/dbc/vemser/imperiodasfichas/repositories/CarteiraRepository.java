package br.com.dbc.vemser.imperiodasfichas.repositories;

import br.com.dbc.vemser.imperiodasfichas.database.ConexaoDataBase;

import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CarteiraRepository implements GenericRepository<Integer, CarteiraEntity> {
    private final ConexaoDataBase conexaoDataBase;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_CARTEIRA.nextval mysequence from DUAL";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }
        return null;
    }

    @Override
    public CarteiraEntity adicionar(CarteiraEntity carteira) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();
            con.setAutoCommit(false);

            Integer proximoId = this.getProximoId(con);
            carteira.setIdCarteira(proximoId);

            String sql = "INSERT INTO CARTEIRA (ID, FICHAS, DINHEIRO, ID_JOGADOR) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, carteira.getIdCarteira());
            stmt.setInt(2, carteira.getFichas());
            stmt.setDouble(3, carteira.getDinheiro());
            stmt.setInt(4, carteira.getIdJogador());

            stmt.executeUpdate();
            con.commit();
            return carteira;
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

            String sql = "DELETE FROM CARTEIRA WHERE ID = ?";
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
    public CarteiraEntity editar(Integer idCarteira, CarteiraEntity carteiraAtualizar) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoDataBase.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CARTEIRA SET ");
            sql.append(" fichas = ?,");
            sql.append(" dinheiro = ? ");
            sql.append(" WHERE id = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, carteiraAtualizar.getFichas());
            stmt.setDouble(2, carteiraAtualizar.getDinheiro());
            stmt.setInt(3, idCarteira);

            stmt.executeUpdate();

            carteiraAtualizar.setIdCarteira(idCarteira);
            return carteiraAtualizar;
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
    public CarteiraEntity buscarPorId(Integer id) throws RegraDeNegocioException {
        CarteiraEntity carteira = null;
        Connection con = null;
        try {
            // ***** CORREÇÃO APLICADA AQUI *****
            con = conexaoDataBase.getConnection();

            String sql = "SELECT ID, FICHAS, DINHEIRO, ID_JOGADOR FROM CARTEIRA WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                carteira = new CarteiraEntity(
                        res.getInt("ID"),
                        res.getInt("FICHAS"),
                        res.getDouble("DINHEIRO"),
                        res.getInt("ID_JOGADOR")
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
        return carteira;
    }

    public CarteiraEntity buscarCarteiraPorIdJogador(Integer idJogador) throws RegraDeNegocioException {
        CarteiraEntity carteira = null;
        Connection con = null;
        try {
            // ***** CORREÇÃO APLICADA AQUI *****
            con = conexaoDataBase.getConnection();

            String sql = "SELECT ID, FICHAS, DINHEIRO, ID_JOGADOR FROM CARTEIRA WHERE ID_JOGADOR = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idJogador);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                carteira = new CarteiraEntity(
                        res.getInt("ID"),
                        res.getInt("FICHAS"),
                        res.getDouble("DINHEIRO"),
                        res.getInt("ID_JOGADOR")
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
        return carteira;
    }

    @Override
    public List<CarteiraEntity> listar() throws RegraDeNegocioException {
        List<CarteiraEntity> carteiras = new ArrayList<>();
        Connection con = null;
        try {
            // ***** CORREÇÃO APLICADA AQUI *****
            con = conexaoDataBase.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT ID, FICHAS, DINHEIRO, ID_JOGADOR FROM CARTEIRA";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                CarteiraEntity carteira = new CarteiraEntity(
                        res.getInt("ID"),
                        res.getInt("FICHAS"),
                        res.getDouble("DINHEIRO"),
                        res.getInt("ID_JOGADOR")
                );
                carteiras.add(carteira);
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
        return carteiras;
    }
}