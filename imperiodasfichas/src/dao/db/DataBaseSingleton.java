package dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Jogador;
import model.jogos.Jogo;

public class DataBaseSingleton {

    private static volatile DataBaseSingleton instance;

    private static volatile Connection connection;
    private final Map<String, Jogador> jogadores;
    private final Map<String, Jogo> jogos;

    private static final String SERVER = "localhost";
    private static final String PORT = "1521";
    private static final String DATABASE = "xe";

    private static final String USER = "system";
    private static final String PASS = "oracle";
    private static final String SCHEMA = "JOGO";

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;

        // Abre-se a conexão com o Banco de Dados
        Connection con = DriverManager.getConnection(url, USER, PASS);

        con.createStatement().execute("alter session set current_schema=" + SCHEMA);

        return con;
    }

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
