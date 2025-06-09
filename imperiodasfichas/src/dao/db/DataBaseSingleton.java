package dao.db;

import java.util.ArrayList;
import java.util.List;

import model.Jogador;
import model.jogos.Jogo;

public class DataBaseSingleton {
    private static volatile DataBaseSingleton instance;
    private final List<Jogador> jogadores;
    private final List<Jogo> jogos;

    private DataBaseSingleton() {
        this.jogadores = new ArrayList<>();
        this.jogos = new ArrayList<>();
    }

    public static DataBaseSingleton getInstance() {
        if (instance == null) {
            synchronized (DataBaseSingleton.class) {
                if (instance == null) {
                    instance = new DataBaseSingleton();
                }
            }
        }
        return instance;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }
    public List<Jogo> getJogos() {
        return jogos;
    }
    public void reset() {
        jogadores.clear();
        jogos.clear();
    }
}
