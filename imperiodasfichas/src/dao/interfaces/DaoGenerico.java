package dao.interfaces;

import java.util.List;


public interface DaoGenerico<T, K> {
    
    T adicionar(T entidade);
    
    boolean remover(T entidade);
    
    T buscar(K key);
    
    T atualizar(T entidade, K key);
    
    List<T> listar();
}
