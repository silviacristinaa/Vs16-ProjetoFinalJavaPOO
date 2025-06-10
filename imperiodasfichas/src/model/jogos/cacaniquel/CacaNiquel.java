package model.jogos.cacaniquel;

import model.Jogador;
import model.Partida;
import model.jogos.Jogo;
import java.util.Random;

import exceptions.ValorInvalido;

public class CacaNiquel extends Jogo {

    private String[] simbolos = {"🍒", "⭐", "💎", "🔔"};

    public CacaNiquel(String nomeJogo, String regras) {
        super(nomeJogo, regras, 10);
    }

    @Override
    public Partida jogar(Jogador jogador,int valorApostado, int opcaoEscolhida) {
        int resultadoCodificado = girarRolos();
        jogador.getCarteira().removerFichas(valorApostado);
        boolean ganhou = verificarResultado(resultadoCodificado, opcaoEscolhida);
        Partida partida = new Partida(valorApostado, ganhou, this, jogador);
        exibirResultado(partida, resultadoCodificado);
        return partida;
    }

    private int girarRolos() {
        Random random = new Random();

        System.out.println("Girando...");

        int rolo1 = random.nextInt(simbolos.length);
        int rolo2 = random.nextInt(simbolos.length);
        int rolo3 = random.nextInt(simbolos.length);

        int resultadoCodificado = rolo1 * 100 + rolo2 * 10 + rolo3;

        return resultadoCodificado;
    }

    @Override
    public void apostaValida(int valorApostado, int opcaoEscolhida) throws ValorInvalido {
        validarValor(valorApostado);
    }

    @Override
    public void validarOpcao(int opcaoEscolhida) {
        throw new UnsupportedOperationException("O caça-níquel não possui opções de aposta válidas.");
    }

    @Override
    public boolean verificarResultado(int resultado, int opcaoEscolhida) {
        int[] arrayResultado = pegarResultadoDescodificado(resultado);

        return arrayResultado[0] == arrayResultado[1] && arrayResultado[1] == arrayResultado[2];
    }

    @Override
    public void exibirResultado(Partida partida, int resultado) {
        int[] arrayResultado = pegarResultadoDescodificado(resultado);

        String resultadoEmoji = simbolos[arrayResultado[0]] + " | " + simbolos[arrayResultado[1]] + " | " + simbolos[arrayResultado[2]];

        if (partida.isGanhou()) {
            int premio = partida.getQuantidadeFichasApostado() * 2;
            partida.getJogador().getCarteira().adicionarFichas(premio);
            System.out.printf("Parabéns! Você ganhou! \nResultado do Caça Níquel: %s\nPrêmio: %d fichas\n", resultadoEmoji, premio);
        } else {
            System.out.printf("Você perdeu. Resultado do Caça Níquel: %s\n", resultadoEmoji);
        }
    }

    private static int[] pegarResultadoDescodificado(int resultado) {
        int r1 = resultado / 100;
        int r2 = (resultado / 10) % 10;
        int r3 = resultado % 10;

        int[] arrayResultado = {r1, r2, r3};

        return arrayResultado;
    }
}
