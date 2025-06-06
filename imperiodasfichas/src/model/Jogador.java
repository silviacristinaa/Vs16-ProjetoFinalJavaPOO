package model;

public class Jogador {

    private String nome;
    private int idade;
    private Carteira carteira;

    public Jogador(String nome, int idade, Carteira carteira) {
        this.nome = nome;
        this.idade = idade;
        this.carteira = carteira;
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
