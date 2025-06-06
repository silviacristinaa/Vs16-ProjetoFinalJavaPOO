package model;

abstract class Jogo {
    private String nomeJogo;
    private String regras;
    private int valorInicial;

    public Jogo(String nomeJogo, String regras, int valorInicial) {
        this.nomeJogo = nomeJogo;
        this.regras = regras;
        this.valorInicial = valorInicial;
    }

    public abstract void jogar();

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
}