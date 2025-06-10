package model.jogos.roletas;

import exceptions.ValorInvalido;
import model.Jogador;
import model.Partida;
import model.jogos.Jogo;

public class RoletaParImpar extends Jogo {

    public RoletaParImpar(String nomeJogo, String regras) {
        super(nomeJogo, regras, 5);
    }


    @Override
    public Partida jogar(Jogador jogador,int valorApostado, int opcaoEscolhida) throws ValorInvalido {
        apostaValida(valorApostado, opcaoEscolhida);
        jogador.getCarteira().removerFichas(valorApostado);
        int resultadoRoleta = girarRoleta();
        boolean ganhou = verificarResultado(resultadoRoleta, opcaoEscolhida);
        Partida partida = new Partida(valorApostado, ganhou, this, jogador);
        exibirResultado(partida ,resultadoRoleta);
        return partida;
    }

    @Override
    public void apostaValida(int valorApostado, int opcaoEscolhida) throws ValorInvalido {
        validarValor(valorApostado);
        validarOpcao(opcaoEscolhida);
    }

    @Override
    public void validarOpcao(int opcaoEscolhida) throws ValorInvalido {
        if (opcaoEscolhida < 0 || opcaoEscolhida > 1) {
            throw new ValorInvalido("Opção inválida. Escolha 0 para PAR ou 1 para ÍMPAR.");
        }
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
        try {
            return AnimacaoRoletaParImpar.executar();
        } catch (InterruptedException e) {
            return (int) (Math.random() * 36);
        }
    }
}