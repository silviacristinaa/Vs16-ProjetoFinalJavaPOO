package model;

import java.time.LocalDateTime;

public class Partida {
    private LocalDateTime dataHora;
    private int quantidadeFichasApostado;
    private int resultadoPartida;
    private String nomeJogo;

    public Partida(LocalDateTime dataHora, int quantidadeFichasApostado, int resultadoPartida, String nomeJogo) {
        this.dataHora = dataHora;
        this.quantidadeFichasApostado = quantidadeFichasApostado;
        this.resultadoPartida = resultadoPartida;
        this.nomeJogo = nomeJogo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public int getQuantidadeFichasApostado() {
        return quantidadeFichasApostado;
    }

    public int getResultadoPartida() {
        return resultadoPartida;
    }

    public String getNomeJogo() {
        return nomeJogo;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setQuantidadeFichasApostado(int quantidadeFichasApostado) {
        this.quantidadeFichasApostado = quantidadeFichasApostado;
    }

    public void setResultadoPartida(int resultadoPartida) {
        this.resultadoPartida = resultadoPartida;
    }

    public void setNomeJogo(String nomeJogo) {
        this.nomeJogo = nomeJogo;
    }

    @Override
    public String toString() {
        return "Nome do Jogo: " + this.nomeJogo + "\nHorário: " + this.dataHora + "\nFichas Apostadas: " + this.quantidadeFichasApostado + "\nGanhos/Perdas: " + this.resultadoPartida;
    }
}
