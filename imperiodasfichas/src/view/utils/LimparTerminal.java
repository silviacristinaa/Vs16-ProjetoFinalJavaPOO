package view.utils;

public class LimparTerminal {
    public static void executar() {
        String mensagem = "\n\nCarregando";
        System.out.print(mensagem);

        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.print(".");
        }

        System.out.print(" [");
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.print("█");
        }
        System.out.println("]");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (int i = 0; i < 50; i++) System.out.println();
    }
}