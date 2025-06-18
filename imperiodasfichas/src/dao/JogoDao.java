package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.db.DataBaseSingleton;
import dao.interfaces.DaoGenerico;
import model.jogos.Jogo;

public class JogoDao implements DaoGenerico<Jogo, String> {

    private final DataBaseSingleton dataBase = DataBaseSingleton.getInstance();

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        return 0;
    }

    @Override
    public Jogo adicionar(Jogo entidade) {
        return dataBase.addJogo(entidade);
    }

    @Override
    public boolean remover(Jogo jogo) {
        return dataBase.removeJogo(jogo.getNomeJogo());
    }

    @Override
    public Jogo buscar(String nomeDoJogo) {
        return dataBase.getJogo(nomeDoJogo);
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
