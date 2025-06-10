package model.jogos.roletas;

import exceptions.ValorInvalido;
import model.Jogador;
import model.Partida;
import model.jogos.Jogo;

public class RoletaCores extends Jogo {

    public RoletaCores(String nomeJogo, String regras) {
        super(nomeJogo, regras, 10);
    }

    @Override
    public void exibirRegras() {
        System.out.println("Regras do jogo " + getNomeJogo() + ": " + getRegras());
    }

    @Override
    public Partida jogar(Jogador jogador,int valorApostado, int opcaoEscolhida) throws ValorInvalido {
        apostaValida(valorApostado, opcaoEscolhida);
        jogador.getCarteira().removerFichas(valorApostado);
        int resultadoRoleta = girarRoleta();
        boolean ganhou = verificarResultado(resultadoRoleta, opcaoEscolhida);
        Partida partida = new Partida(valorApostado, ganhou, this, jogador);
        exibirResultado(partida, resultadoRoleta);
        return partida;
    }

    @Override
    public void apostaValida(int valorApostado, int opcaoEscolhida) throws ValorInvalido {
        validarValor(valorApostado);
        validarOpcao(opcaoEscolhida);
    }

    @Override
    public void validarOpcao(int opcaoEscolhida) throws ValorInvalido {
        if (opcaoEscolhida < 0 || opcaoEscolhida >= CoresDaRoleta.values().length) {
            throw new ValorInvalido("Opção inválida. Escolha um número entre 0 e " + (CoresDaRoleta.values().length - 1));
        }
    }

    @Override
    public boolean verificarResultado(int resultado, int opcaoEscolhida) {
        return resultado == opcaoEscolhida;
    }

    @Override
    public void exibirResultado(Partida partida, int resultado) {
        String corResultado = CoresDaRoleta.values()[resultado].name();
        if (partida.isGanhou()) {
            int premio = partida.getQuantidadeFichasApostado() * 4;
            partida.getJogador().getCarteira().adicionarFichas(premio);
            System.out.printf("Parabéns! Você ganhou! \nPrêmio: %d fichas\n", premio);
        } else {
            System.out.print("Você perdeu. :< - tente novamente!");
        }
    }

    private int girarRoleta() {
        try {
            return AnimacaoRoletaCores.executar();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return (int) (Math.random() * 4);
        }
    }
}