package model.jogos.cacaniquel;

import model.Jogador;
import model.Partida;
import model.jogos.Jogo;
import java.util.Random;

public class CacaNiquel extends Jogo {

    public static final String[] simbolos = {"🍒", "⭐", "💎", "🔔"};

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
    public boolean apostaValida(int valorApostado, int opcaoEscolhida) {
        return validarValor(valorApostado);
    }

    @Override
    public boolean validarOpcao(int opcaoEscolhida) {
        return true;
    }

    @Override
    public boolean verificarResultado(int resultado, int opcaoEscolhida) {
        int[] arrayResultado = pegarResultadoDescodificado(resultado);

        return arrayResultado[0] == arrayResultado[1] && arrayResultado[1] == arrayResultado[2];
    }

    @Override
    public void exibirResultado(Partida partida, int resultado) {
        int[] arrayResultado = pegarResultadoDescodificado(resultado);

        try {
            AnimacaoCacaNiquel.executar(arrayResultado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String resultadoEmoji = simbolos[arrayResultado[0]] + " | " + simbolos[arrayResultado[1]] + " | " + simbolos[arrayResultado[2]];

        if (partida.isGanhou()) {
            int premio = partida.getQuantidadeFichasApostado() * 2;
            partida.getJogador().getCarteira().adicionarFichas(premio);
            System.out.printf("\nParabéns! Você ganhou! \nPrêmio: %d fichas\n", premio);
        } else {
            System.out.print("\nVocê perdeu, é uma pena. Tente novamente! \n");
        }
    }

    private static int[] pegarResultadoDescodificado(int resultado) {
        int r1 = resultado / 100;
        int r2 = (resultado / 10) % 10;
        int r3 = resultado % 10;

        int[] arrayResultado = {r1, r2, r3};

        return arrayResultado;
    }

    public static String[] getSimbolos() {
        return simbolos;
    }
}
