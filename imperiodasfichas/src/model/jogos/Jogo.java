package model.jogos;

import model.Jogador;
import model.Partida;

import java.util.Objects;

public abstract class Jogo {
    private Integer idJogo;
    private String nomeJogo;
    private String regras;
    private int valorInicial;

    public Integer getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Integer idJogo) {
        this.idJogo = idJogo;
    }

    protected Jogo(Integer idJogo, String nomeJogo, String regras, int valorInicial) {
        this.idJogo = idJogo;
        this.nomeJogo = nomeJogo;
        this.regras = regras;
        this.valorInicial = valorInicial;
    }

    public Jogo() {
    }

    public void exibirRegras() {
        System.out.println("Regras do jogo " + nomeJogo + ": " + regras);
    }

    public abstract Partida jogar(Jogador jogador ,int valorApostado, int opcaoEscolhida) throws IllegalArgumentException;

    public abstract void apostaValida(int valorApostado, int opcaoEscolhida) throws IllegalArgumentException;

    protected void validarValor(int valorApostado) throws IllegalArgumentException {
        if (valorApostado < getValorInicial()) {
            throw new IllegalArgumentException("Valor apostado deve ser maior ou igual ao valor inicial.");
        }
    }

    public abstract void validarOpcao(int opcaoEscolhida) throws IllegalArgumentException;
    public abstract boolean verificarResultado(int resultado, int opcaoEscolhida);

    public abstract void exibirResultado(Partida partida, int resultado);

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