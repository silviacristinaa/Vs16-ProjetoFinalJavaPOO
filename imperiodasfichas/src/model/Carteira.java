package model;

public class Carteira {
    private int fichas;
    private double dinheiro;

    public Carteira() {
        this.fichas = 1000;
        this.dinheiro = 0;
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

    public boolean depositarFichasCompradas(int quantidadeFicha, double valorFicha) {
        if (quantidadeFicha < 0) {
            return false;
        }
        this.fichas += quantidadeFicha;
        this.dinheiro -= quantidadeFicha * valorFicha;
        return true;
    }

    public boolean sacarFichasVendidas(int quantidadeFicha, double valorFicha) {
        if (this.fichas >= quantidadeFicha && quantidadeFicha > 0) {
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
        return ">> Fichas atuais: " + fichas + "\n>> Dinheiro atual: " + dinheiro;
    }
}
