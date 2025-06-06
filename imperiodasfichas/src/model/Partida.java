package model;

import java.time.LocalDateTime;

public class Partida {
    private LocalDateTime dataHora;
    private int quantidadeFichasApostado;
    private int resultadoPartida;
    private Jogo jogo;
    private Jogador jogador;

    public Partida(LocalDateTime dataHora, int quantidadeFichasApostado, int resultadoPartida, Jogo jogo, Jogador jogador) {
        this.dataHora = dataHora;
        this.quantidadeFichasApostado = quantidadeFichasApostado;
        this.resultadoPartida = resultadoPartida;
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

    public int getResultadoPartida() {
        return resultadoPartida;
    }

    public void setResultadoPartida(int resultadoPartida) {
        this.resultadoPartida = resultadoPartida;
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
        return "Nome do Jogo: " + this.jogo.getNomeJogo() + "\nHorário: " + this.dataHora + "\nFichas Apostadas: " + this.quantidadeFichasApostado + "\nGanhos/Perdas: " + this.resultadoPartida;
    }
}
