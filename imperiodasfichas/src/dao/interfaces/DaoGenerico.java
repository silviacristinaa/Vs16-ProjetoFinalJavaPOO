package dao.interfaces;

import java.util.List;


public interface DaoGenerico<T, K> {
    
    boolean adicionar(T entidade);
    
    boolean remover(K key);
    
    T buscar(K key);
    
    T atualizar(T entidade, K key);
    
    List<T> listar();
}
