package dao;

import java.util.List;

import dao.db.DataBaseSingleton;
import dao.interfaces.DaoGenerico;
import model.Jogador;

public class JogadorDao implements DaoGenerico<Jogador, String> {

    private DataBaseSingleton dataBase = DataBaseSingleton.getInstance();

    @Override
    public Jogador adicionar(Jogador entidade) {
        return dataBase.addJogador(entidade);
    }

    @Override
    public boolean remover(Jogador jogador) {
        return dataBase.removeJogador(jogador.getNickname());
    }

    @Override
    public Jogador buscar(String key) {
        return dataBase.getJogador(key);
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
