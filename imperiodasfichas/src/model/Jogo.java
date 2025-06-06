package model;

import java.util.Objects;

public abstract class Jogo {
    private String nomeJogo;
    private String regras;
    private int valorInicial;

    protected Jogo(String nomeJogo, String regras, int valorInicial) {
        this.nomeJogo = nomeJogo;
        this.regras = regras;
        this.valorInicial = valorInicial;
    }

    public void exibirRegras() {
        System.out.println("Regras do jogo " + nomeJogo + ": " + regras);
    }

    public abstract boolean jogar(int ValorApostado, int opcaoEscolhida);


    public int getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(int valorInicial) {
        this.valorInicial = valorInicial;
    }

    public String getNomeJogo() {
        return nomeJogo;
    }

    public void setNomeJogo(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }

    public String getRegras() {
        return regras;
    }

    public void setRegras(String regras) {
        this.regras = regras;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Jogo jogo)) return false;
        return Objects.equals(nomeJogo, jogo.nomeJogo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nomeJogo);
    }
}