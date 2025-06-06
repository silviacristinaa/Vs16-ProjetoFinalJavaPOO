package model;

import java.util.ArrayList;

public class Jogador {

    private String nome;
    private String nickname;
    private int idade;
    private Carteira carteira;
    private ArrayList<Partida> partidas;

    public Jogador(String nome, int idade, double fichas) {
        this.nome = nome;
        this.idade = idade;
        this.carteira = new Carteira();
        this.partidas = new ArrayList<>();
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
