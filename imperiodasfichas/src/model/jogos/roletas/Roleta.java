package model.jogos.roletas;

import model.jogos.Jogo;

public class Roleta extends Jogo {

    public Roleta(String nomeJogo, String regras) {
        super(nomeJogo, regras, 5);
    }

    public void exibirRegras() {
        System.out.println("Regras do jogo " + getNomeJogo() + ": " + getRegras());
    }

    @Override
    public boolean jogar(int valorApostado, int opcaoEscolhida) {
        if (!apostaValida(valorApostado, opcaoEscolhida)) {
            return false;
        }

        int resultadoRoleta = girarRoleta();
        boolean ganhou = verificarResultado(resultadoRoleta, opcaoEscolhida);

        exibirResultado(ganhou, resultadoRoleta, valorApostado);
        return ganhou;
    }

    @Override
    public boolean apostaValida(int valorApostado, int opcaoEscolhida) {
        return validarValor(valorApostado) && validarOpcao(opcaoEscolhida);
    }

    @Override
    public boolean validarOpcao(int opcaoEscolhida) {
        if (opcaoEscolhida != 0 && opcaoEscolhida != 1) {
            System.out.println("Opção inválida. Escolha 0 para PAR ou 1 para ÍMPAR.");
            return false;
        }
        return true;
    }

    @Override
    public boolean verificarResultado(int resultado, int opcaoEscolhida) {
        boolean isPar = resultado % 2 == 0;
        if (isPar && opcaoEscolhida == 0) {
            return true;
        } else return !isPar && opcaoEscolhida == 1;
    }

    @Override
    public void exibirResultado(boolean ganhou, int resultado, int valorApostado) {
        if (ganhou) {
            int premio = valorApostado * 2;
            System.out.printf("Parabéns! Você ganhou. Resultado: %d - Prêmio: %d\n", resultado, premio);
        } else {
            System.out.printf("Você perdeu. Resultado da roleta: %d\n", resultado);
        }
    }

    private int girarRoleta() {
        int resultado = (int) (Math.random() * 36); // 0 a 35
        System.out.println("Girando a roleta...");
        return resultado;
    }
}