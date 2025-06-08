package model.jogos.roletas;

import model.jogos.Jogo;

public class RoletaCores extends Jogo {

    public enum CoresDaRoleta {
        VERMELHO, AZUL, AMARELO, VERDE
    }

    public RoletaCores(String nomeJogo, String regras) {
        super(nomeJogo, regras, 10);
    }

    @Override
    public void exibirRegras() {
        System.out.println("Regras do jogo " + getNomeJogo() + ": " + getRegras());
    }

    @Override
    public boolean jogar(int valorApostado, int opcaoEscolhida) {
        if (apostaValida(valorApostado, opcaoEscolhida)) {
            return false;
        }

        int resultadoRoleta = girarRoleta();
        boolean ganhou = verificarResultado(resultadoRoleta, opcaoEscolhida);

        exibirResultado(ganhou, resultadoRoleta, valorApostado);
        return ganhou;
    }

    @Override
    public boolean apostaValida(int valorApostado, int opcaoEscolhida) {
        return !validarValor(valorApostado) || !validarOpcao(opcaoEscolhida);
    }

    @Override
    public boolean validarOpcao(int opcaoEscolhida) {
        return opcaoEscolhida >= 0 && opcaoEscolhida < CoresDaRoleta.values().length;
    }

    @Override
    public boolean verificarResultado(int resultado, int opcaoEscolhida) {
        return resultado == opcaoEscolhida;
    }

    @Override
    public void exibirResultado(boolean ganhou, int resultado, int valorApostado) {
        String corResultado = CoresDaRoleta.values()[resultado].name();
        if (ganhou) {
            int premio = valorApostado * 4;
            System.out.printf("Parabéns! Você ganhou! \nResultado da Roleta: %s\nPrêmio: %d fichas\n", corResultado, premio);
        } else {
            System.out.printf("Você perdeu. Resultado da roleta: %s\n", corResultado);
        }
    }

    private int girarRoleta() {
        System.out.println("Girando a roleta...");
        CoresDaRoleta[] cores = CoresDaRoleta.values();
        return (int) (Math.random() * cores.length);
    }
}