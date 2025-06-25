package br.com.dbc.vemser.imperiodasfichas.entities;

public class CarteiraEntity {

    private Integer idCarteira;
    private int fichas;
    private double dinheiro;
    private Integer idJogador;

    public CarteiraEntity() {
        this.fichas = 10;
        this.dinheiro = 0;
    }

    public CarteiraEntity(int fichas, double dinheiro) {
        this.fichas = fichas;
        this.dinheiro = dinheiro;
    }

    public CarteiraEntity(Integer idCarteira, int fichas, double dinheiro, Integer idJogador) {
        this.idCarteira = idCarteira;
        this.fichas = fichas;
        this.dinheiro = dinheiro;
        this.idJogador = idJogador;
    }

    public Integer getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(Integer idCarteira) {
        this.idCarteira = idCarteira;
    }

    public int getFichas() {
        return fichas;
    }

    public double getDinheiro() {
        return dinheiro;
    }

    public Integer getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Integer idJogador) {
        this.idJogador = idJogador;
    }

    @Override
    public String toString() {
        return ">> 🎟️ Fichas atuais: " + fichas + "\n>> 💵 Dinheiro disponível: R$ " + dinheiro;
    }
}
