package model;

import model.jogos.Jogo;

import java.time.LocalDateTime;

public class Partida {
    private LocalDateTime dataHora;
    private int quantidadeFichasApostado;
    private boolean ganhou;
    private Jogo jogo;
    private Jogador jogador;

    public Partida(int quantidadeFichasApostado, boolean ganhou, Jogo jogo, Jogador jogador) {
        this.dataHora = LocalDateTime.now();
        this.quantidadeFichasApostado = quantidadeFichasApostado;
        this.ganhou = ganhou;
        this.jogo = jogo;
        this.jogador = jogador;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getQuantidadeFichasApostado() {
        return quantidadeFichasApostado;
    }

    public void setQuantidadeFichasApostado(int quantidadeFichasApostado) {
        this.quantidadeFichasApostado = quantidadeFichasApostado;
    }

    public boolean isGanhou() {
        return ganhou;
    }

    public void setGanhou(boolean resultadoPartida) {
        this.ganhou = resultadoPartida;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    @Override
    public String toString() {
        return "Nome do Jogo: " + this.jogo.getNomeJogo() + "\nHorário: " + this.dataHora + "\nFichas Apostadas: " + this.quantidadeFichasApostado + "\nGanhos/Perdas: " + this.ganhou;
    }
}
