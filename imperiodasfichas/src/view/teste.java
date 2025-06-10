package view;

import java.util.concurrent.TimeUnit;

public class teste {

    public static void main(String[] args) throws InterruptedException {
        clearScreen();
        printWithDelay("\u001B[35m", "Iniciando...\n\n", 50);
        showLogo();
        printWithDelay("\u001B[36m", "\nBem-vindo ao 🎰 IMPÉRIO DAS FICHAS 🎰\n", 50);
        printWithDelay("\u001B[32m", "\nCarregando cassino virtual...\n", 50);
        loadingBar();
        System.out.println("\u001B[0m");
    }

    private static void printWithDelay(String color, String message, int delayMillis) throws InterruptedException {
        System.out.print(color);
        for (char c : message.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            TimeUnit.MILLISECONDS.sleep(delayMillis);
        }
    }

    private static void loadingBar() throws InterruptedException {
        System.out.print("\u001B[33m[");
        for (int i = 0; i < 30; i++) {
            System.out.print("█");
            System.out.flush();
            TimeUnit.MILLISECONDS.sleep(80);
        }
        System.out.println("] \u001B[32mOK!\u001B[0m");
    }

    private static void showLogo() throws InterruptedException {

        String[] logo = {
                // IMPERIO
                "\u001B[31m██╗███╗   ███╗██████╗ ███████╗██████╗ ██╗ ██████╗ ",
                "\u001B[33m██║████╗ ████║██╔══██╗██╔════╝██╔══██╗██║██╔═══██╗ ",
                "\u001B[32m██║██╔████╔██║██████╔╝█████╗  ██████╔╝██║██║   ██╗",
                "\u001B[36m██║██║╚██╔╝██║██╔═══╝ ██╔══╝  ██╔══██╗██║██║   ██║",
                "\u001B[34m██║██║ ╚═╝ ██║██║     ███████╗██║  ██║██║ ██████═╗",
                "\u001B[35m╚═╝╚═╝     ╚═╝╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝ ╚══════╝",
                " ",
                // DAS
                "\u001B[31m██████╗ █████╗  ███████╗",
                "\u001B[33m██╔══██╗██╔══██╗██╔════╝",
                "\u001B[32m██║  ██║███████║███████╗",
                "\u001B[36m██║  ██║██╔══██║╚════██║",
                "\u001B[34m██████═║██║  ██║███████║",
                "\u001B[35m╚═════╝ ╚═╝  ╚═╝╚══════╝",
                " ", // Spacer line
                // FICHAS
                "\u001B[31m███████╗██╗ ██████╗██╗  ██╗ █████╗ ███████╗",
                "\u001B[33m██╔════╝██║██╔════╝██║  ██║██╔══██╗██╔════╝",
                "\u001B[32m█████╗  ██║██║     ███████║███████║███████╗",
                "\u001B[36m██╔══╝  ██║██║     ██╔══██║██╔══██║╚════██║",
                "\u001B[34m██║     ██║╚██████╗██║  ██║██║  ██║███████╗",
                "\u001B[35m╚═╝     ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝",
                " "
        };

        for (String line : logo) {
            System.out.println(line);
            TimeUnit.MILLISECONDS.sleep(100); // Adjusted delay slightly as the logo is taller
        }

    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

