package dao;

import java.util.List;

import dao.db.DataBaseSingleton;
import dao.interfaces.DaoGenerico;
import model.Jogador;

public class JogadorDao implements DaoGenerico<Jogador, String> {

    private DataBaseSingleton dataBase = DataBaseSingleton.getInstance();

    @Override
    public boolean adicionar(Jogador entidade) {
        if (buscar(entidade.getNickname()) == null) {
            dataBase.getJogadores().add(entidade);
            return true;
        } 
        return false;
    }

    @Override
    public boolean remover(String nickname) {
        Jogador jogador = buscar(nickname);
        if (jogador != null) {
            dataBase.getJogadores().remove(jogador);
            return true;
        }
        return false;
    }

    @Override
    public Jogador buscar(String key) {
        if (!dataBase.getJogadores().isEmpty()) {
            for (Jogador jogador : dataBase.getJogadores()) {
                if (jogador.getNickname().equals(key)) {
                    return jogador;
                }
            }
        }
        return null;
    }

    @Override
    public Jogador atualizar(Jogador entidade, String key) {
        Jogador jogadorExistente = buscar(key);
        if (jogadorExistente != null) {
            jogadorExistente.setNome(entidade.getNome());
            jogadorExistente.setIdade(entidade.getIdade());
            return jogadorExistente;
        }
        return null;
    }

    @Override
    public List<Jogador> listar() {
        return dataBase.getJogadores();
    }
    
   
    
}
