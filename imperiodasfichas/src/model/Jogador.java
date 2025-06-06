package model;

import java.util.ArrayList;

public class Jogador {

    private String nome;
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
}
