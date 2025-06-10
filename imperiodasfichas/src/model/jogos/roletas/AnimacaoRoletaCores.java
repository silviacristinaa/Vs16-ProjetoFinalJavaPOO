package model.jogos.roletas;

import java.util.List;
import java.util.Arrays;

public class AnimacaoRoletaCores {

    private static class Cor {
        String nome;
        String ansi;

        Cor(String nome, String ansi) {
            this.nome = nome;
            this.ansi = ansi;
        }

        String formatado() {
            int padding = AnimacaoRoletaCores.LARGURA_COR - nome.length();
            int esquerda = padding / 2;
            int direita = padding - esquerda;
            return ansi + " ".repeat(esquerda) + nome + " ".repeat(direita) + ANSI_RESET;
        }
    }

    private static final String ANSI_RESET = "\u001B[0m";

    private static final List<Cor> CORES = Arrays.asList(
            new Cor("VERMELHO", "\u001B[31m"),
            new Cor("AZUL",     "\u001B[34m"),
            new Cor("AMARELO",  "\u001B[33m"),
            new Cor("VERDE",    "\u001B[32m")
    );

    private static final int LARGURA_COR = CORES.stream()
            .mapToInt(c -> c.nome.length())
            .max()
            .orElse(0) + 2;

    private static final String[] SPINNER = { "/", "-", "\\", "|" };

    public static int executar() throws InterruptedException {
        int totalCores = CORES.size();
        int resultado = (int) (Math.random() * totalCores);
        int voltas = 6 + (int) (Math.random() * 3);
        int totalPassos = voltas * totalCores + resultado + 1;

        int delay = 80;
        int incremento = 40;
        int desaceleraNosUltimos = totalCores * 3;

        System.out.println("🟥 🟦 🟨 🟩 GIRANDO A ROLETA DA SORTE!  🟩 🟨 🟦 🟥");
        System.out.println("════════════════════════════════════════════════");

        for (int i = 0; i < totalPassos; i++) {
            int centro = i % totalCores;
            int anterior = (centro - 1 + totalCores) % totalCores;
            int proximo = (centro + 1) % totalCores;

            String spinner = SPINNER[i % SPINNER.length];

            String linha = String.format("   [%s] ➤[%s] [%s]  %s",
                    CORES.get(anterior).formatado(),
                    CORES.get(centro).formatado(),
                    CORES.get(proximo).formatado(),
                    spinner
            );

            System.out.print("\r" + linha);
            System.out.flush();

            Thread.sleep(delay);

            if (i > totalPassos - desaceleraNosUltimos) {
                delay += incremento;
            }
        }

        System.out.println("\n\n════════════════════════════════════════════════");
        System.out.println("   🌟🌟🌟 RESULTADO FINAL DA ROLETA 🌟🌟🌟");
        System.out.println("════════════════════════════════════════════════");
        System.out.printf("\n        🎯 A cor sorteada é: %s%s%s!%n",
                CORES.get(resultado).ansi, CORES.get(resultado).nome, ANSI_RESET);
        System.out.println("\n════════════════════════════════════════════════");

        return resultado;
    }
}
