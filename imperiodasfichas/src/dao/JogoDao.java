package dao;

import java.util.List;

import dao.db.DataBaseSingleton;
import dao.interfaces.DaoGenerico;
import model.jogos.Jogo;

public class JogoDao implements DaoGenerico<Jogo, String> {

    private final DataBaseSingleton dataBase = DataBaseSingleton.getInstance();

    @Override
    public Jogo adicionar(Jogo entidade) {
        if (buscar(entidade.getNomeJogo()) == null) {
            dataBase.getJogos().add(entidade);
            return entidade;
        }
        return null;
    }

    @Override
    public boolean remover(Jogo jogo) {
        return dataBase.getJogos().remove(jogo);
    }

    @Override
    public Jogo buscar(String nomeDoJogo) {
        if (!dataBase.getJogos().isEmpty()) {
            for (Jogo jogo : dataBase.getJogos()) {
                if (jogo.getNomeJogo().equals(nomeDoJogo)) {
                    return jogo;
                }
            }
        }
        return null;
    }

    @Override
    public Jogo atualizar(Jogo entidade, String key) {
        Jogo jogoExistente = buscar(key);
        if (jogoExistente != null) {
            jogoExistente.setValorInicial((entidade.getValorInicial()));
            jogoExistente.setRegras(entidade.getRegras());
            return jogoExistente;
        }
        return null;
    }

    @Override
    public List<Jogo> listar() {
        return dataBase.getJogos();
    }
    
}
