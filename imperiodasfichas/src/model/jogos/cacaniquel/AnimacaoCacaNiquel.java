package model.jogos.cacaniquel;

import java.util.Random;

public class AnimacaoCacaNiquel {

    private static final String[] simbolos = CacaNiquel.getSimbolos();
    private static final int RODADAS = 15;
    private static final int DELAY_MS = 120;

    public static void executar(int[] arrayResultado) throws InterruptedException {
        Random random = new Random();
        System.out.println("\n🎰 Girando os rolos...\n");

        for (int i = 0; i < RODADAS - 1; i++) {
            String rolo1 = simbolos[random.nextInt(simbolos.length)];
            String rolo2 = simbolos[random.nextInt(simbolos.length)];
            String rolo3 = simbolos[random.nextInt(simbolos.length)];
            System.out.print("\r" + rolo1 + " | " + rolo2 + " | " + rolo3);
            Thread.sleep(DELAY_MS);
        }

        String rolo1 = simbolos[arrayResultado[0]];
        String rolo2 = simbolos[arrayResultado[1]];
        String rolo3 = simbolos[arrayResultado[2]];
        System.out.print("\r" + rolo1 + " | " + rolo2 + " | " + rolo3);
        Thread.sleep(DELAY_MS);
        System.out.println();
    }
}