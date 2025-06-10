package model;

public class Carteira {
    private int fichas;
    private double dinheiro;

    public Carteira() {
        this.fichas = 10;
        this.dinheiro = 0;
    }
    public Carteira(int fichas, double dinheiro) {
        adicionarFichas(fichas);
        depositarDinheiro(dinheiro);
    }

    public int getFichas() {
        return fichas;
    }

    public double getDinheiro() {
        return dinheiro;
    }

    public boolean depositarDinheiro(double valor) {
        if (valor <= 0) {
            return false;
        }
        this.dinheiro += valor;
        return true;
    }

    public boolean sacarDinheiro(double valor) {
        if (this.dinheiro >= valor) {
            this.dinheiro -= valor;
            return true;
        }
        return false;
    }

    public void depositarFichasCompradas(int quantidadeFicha, double valorFicha) {
        this.fichas += quantidadeFicha;
        this.dinheiro -= quantidadeFicha * valorFicha;
    }

    public boolean sacarFichasVendidas(int quantidadeFicha, double valorFicha) {
        if (this.fichas >= quantidadeFicha) {
            this.fichas -= quantidadeFicha;
            this.dinheiro += quantidadeFicha * valorFicha;
            return true;
        }
        return false;
    }

    public void adicionarFichas(int quantidade) {
        if (quantidade > 0) {
            this.fichas += quantidade;
        }
    }
    public void removerFichas(int quantidade) {
        if (quantidade > 0 && this.fichas >= quantidade) {
            this.fichas -= quantidade;
        }
    }

    @Override
    public String toString() {
        return ">> 🎟️ Fichas atuais: " + fichas + "\n>> 💵 Dinheiro disponível: R$ " + dinheiro;
    }
}
