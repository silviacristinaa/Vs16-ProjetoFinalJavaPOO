package model;

public class Carteira {
    private int fichas;
    private double dinheiro;

    public Carteira() {
        this.fichas = 10;
        this.dinheiro = 0;
    }

    public int getFichas() {
        return fichas;
    }

    public double getDinheiro() {
        return dinheiro;
    }

    public boolean depositarDinheiro(double valor) {
        if (valor < 0) {
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

    public boolean depositarFichas(int valor) {
        if (valor < 0) {
            return false;
        }
        this.fichas += valor;
        return true;
    }

    public boolean sacarFichas(int valor) {
        if (this.fichas >= valor) {
            this.fichas -= valor;
            return true;
        }
        return false;
    }
}
