package model;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Jogador {

    private Integer idJogador;
    private String nome;
    private String nickname;
    private int idade;
    private Carteira carteira;
    private Set<Partida> partidas;

    public Integer getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Integer idJogador) {
        this.idJogador = idJogador;
    }

    public Jogador() {
        this.partidas = new HashSet<>();
    }

    public Jogador(String nome, int idade, String nickname) {
        this.nome = nome;
        this.idade = idade;
        this.nickname = nickname;
        this.carteira = new Carteira();
        this.partidas = new HashSet<>();
    }
    public Jogador(String nome, int idade, String nickname, Carteira carteira) {
        this.nome = nome;
        this.idade = idade;
        this.nickname = nickname;
        this.carteira = carteira;
        this.partidas = new HashSet<>();
    }

    public String getNome() {
        return nome;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public int getIdade() {
        return idade;
    }

    public String getNickname() {
        return nickname;
    }

    public Set<Partida> getPartidas() {
        return partidas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void adicionarPartida(Partida partida) {
        if (partida != null) {
            this.partidas.add(partida);
        }
    }
    public void removerPartida(Partida partida) {
        if (partida != null) {
            this.partidas.remove(partida);
        }
    }

    @Override
    public String toString() {
        return ">> Nickname atual: " + nickname + "\n>> Nome atual: " + nome + "\n>> Idade atual: " + idade;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Jogador other = (Jogador) obj;
        if (nickname == null) {
            if (other.nickname != null)
                return false;
        } else if (!nickname.equalsIgnoreCase(other.nickname))
            return false;
        return true;
    }
    
}
