package model.jogos.roletas;

import model.Jogador;
import model.Partida;
import model.jogos.Jogo;

public class Roleta extends Jogo {

    public Roleta(String nomeJogo, String regras) {
        super(nomeJogo, regras, 5);
    }

    public void exibirRegras() {
        System.out.println("Regras do jogo " + getNomeJogo() + ": " + getRegras());
    }

    @Override
    public Partida jogar(Jogador jogador,int valorApostado, int opcaoEscolhida) {
        if (!apostaValida(valorApostado, opcaoEscolhida)) {
            return null;
        }
        jogador.getCarteira().removerFichas(valorApostado);
        int resultadoRoleta = girarRoleta();
        boolean ganhou = verificarResultado(resultadoRoleta, opcaoEscolhida);
        Partida partida = new Partida(valorApostado, ganhou, this, jogador);
        exibirResultado(partida ,resultadoRoleta);
        return partida;
    }

    @Override
    public boolean apostaValida(int valorApostado, int opcaoEscolhida) {
        return validarValor(valorApostado) && validarOpcao(opcaoEscolhida);
    }

    @Override
    public boolean validarOpcao(int opcaoEscolhida) {
        if (opcaoEscolhida != 0 && opcaoEscolhida != 1) {
            System.out.println("\nOpção inválida. Escolha 0 para PAR ou 1 para ÍMPAR.");
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
    public void exibirResultado(Partida partida, int resultado) {
        if (partida.isGanhou()) {
            int premio = partida.getQuantidadeFichasApostado() * 2;
            partida.getJogador().getCarteira().adicionarFichas(premio);
            System.out.printf("Parabéns! Você ganhou! \nResultado da Roleta: %d\nPrêmio: %d fichas\n", resultado, premio);
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