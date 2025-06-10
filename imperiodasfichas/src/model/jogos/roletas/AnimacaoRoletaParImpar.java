package model.jogos.roletas;

public class AnimacaoRoletaParImpar {
    public static int executar() throws InterruptedException {
        final String[] cores = {
                "\u001B[31m",
                "\u001B[34m",
                "\u001B[33m",
                "\u001B[32m",
                "\u001B[0m"
        };
        int resultado = 0;
        int voltas = 30 + (int)(Math.random() * 10);
        int delay = 40;

        System.out.print("\n🎡 Girando a roleta: ");
        for (int i = 0; i < voltas; i++) {
            resultado = (int) (Math.random() * 36);
            String cor = cores[i % (cores.length - 1)];
            System.out.print("\r🎡 Girando a roleta: " + cor + String.format("%02d", resultado) + "\u001B[0m  ");
            Thread.sleep(delay);
            if (i > voltas - 10) delay += 30;
        }
        System.out.print("\r🎉 Resultado final: \u001B[1m" + String.format("%02d", resultado) + "\u001B[0m      \n");
        return resultado;
    }
}
