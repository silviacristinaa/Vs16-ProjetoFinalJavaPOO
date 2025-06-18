package model;

import model.jogos.Jogo;

import java.time.LocalDateTime;

public class Partida implements Comparable<Partida> {
    private Integer idPartida;
    private LocalDateTime dataHora;
    private int quantidadeFichasApostado;
    private boolean ganhou;
    private Jogo jogo;
    private Jogador jogador;

    public Integer getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    public Partida() {
    }

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataHora == null) ? 0 : dataHora.hashCode());
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
        Partida other = (Partida) obj;
        if (dataHora == null) {
            if (other.dataHora != null)
                return false;
        } else if (!dataHora.equals(other.dataHora))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Nome do Jogo: " + this.jogo.getNomeJogo() + "\nHorário: " + this.dataHora + "\nFichas Apostadas: " + this.quantidadeFichasApostado + "\nGanhos/Perdas: " + this.ganhou;
    }
    @Override
    public int compareTo(Partida outra) {
        return this.dataHora.compareTo(outra.dataHora);
    }
}
