package br.com.dbc.vemser.imperiodasfichas.repositories;

import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<CHAVE, OBJETO>  {

    Integer getProximoId(Connection connection) throws SQLException;

    OBJETO adicionar(OBJETO object) throws RegraDeNegocioException;

    void remover(CHAVE id) throws RegraDeNegocioException;

    OBJETO editar(CHAVE id, OBJETO objeto) throws RegraDeNegocioException;

    OBJETO buscarPorId(CHAVE id) throws RegraDeNegocioException;

    List<OBJETO> listar() throws RegraDeNegocioException;
}
