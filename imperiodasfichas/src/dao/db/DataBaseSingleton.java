package dao.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Jogador;
import model.jogos.Jogo;

public class DataBaseSingleton {
    private static volatile DataBaseSingleton instance;
    private final Map<String, Jogador> jogadores;
    private final Map<String, Jogo> jogos;

    private DataBaseSingleton() {
        this.jogadores = new HashMap<>();
        this.jogos = new HashMap<>();
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
        return new ArrayList<>(jogadores.values());
    }
    public List<Jogo> getJogos() {
        return new ArrayList<>(jogos.values());
    }
    public Jogador addJogador(Jogador jogador) {
        jogadores.put(jogador.getNickname(), jogador);
        return jogadores.get(jogador.getNickname());
    }
    public Jogo addJogo(Jogo jogo) {
        jogos.put(jogo.getNomeJogo(), jogo);
        return jogos.get(jogo.getNomeJogo());
    }
    public Jogador getJogador(String nickname) {
        return jogadores.get(nickname);
    }
    public Jogo getJogo(String nome) {
        return jogos.get(nome);
    }
    public boolean removeJogador(String nickname) {
        return jogadores.remove(nickname) != null;
    }
    public boolean removeJogo(String nome) {
        return jogos.remove(nome) != null;
    }
    public void reset() {
        jogadores.clear();
        jogos.clear();
    }
}
