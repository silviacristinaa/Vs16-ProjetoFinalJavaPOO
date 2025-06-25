package br.com.dbc.vemser.imperiodasfichas.entities;

import java.util.Objects;

public class JogoEntity {

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

    protected JogoEntity(Integer idJogo, String nomeJogo, String regras, int valorInicial) {
        this.idJogo = idJogo;
        this.nomeJogo = nomeJogo;
        this.regras = regras;
        this.valorInicial = valorInicial;
    }

    public JogoEntity() {
    }

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
        if (!(o instanceof JogoEntity jogo)) return false;
        return Objects.equals(nomeJogo, jogo.nomeJogo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nomeJogo);
    }
}
