package dao.interfaces;

import exceptions.RegraDeNegocioException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface DaoGenerico<T, K> {

    Integer getProximoId(Connection connection) throws SQLException;

    T adicionar(T entidade) throws RegraDeNegocioException;
    
    boolean remover(T entidade) throws RegraDeNegocioException;
    
    T buscar(K key) throws RegraDeNegocioException;
    
    T atualizar(T entidade, K key) throws RegraDeNegocioException;
    
    List<T> listar() throws RegraDeNegocioException;
}
