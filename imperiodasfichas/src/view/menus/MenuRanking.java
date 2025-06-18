package view.menus;

import controller.GerenciadorJogador;
import exceptions.RegraDeNegocioException;
import model.Jogador;
import view.utils.LimparTerminal;
import view.utils.AnsiColors;

import java.util.List;
import java.util.Scanner;

public class MenuRanking {

    public static void executarMenu(GerenciadorJogador gerenciadorJogador) throws RegraDeNegocioException {
        LimparTerminal.executar();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n" + AnsiColors.YELLOW + "🏆 === TOP JOGADORES === 🏆" + AnsiColors.RESET + "\n");
        List<Jogador> jogadoresPorVitorias = gerenciadorJogador.listarJogadoresPorVitorias();

        if (jogadoresPorVitorias.isEmpty()) {
            System.out.println(AnsiColors.CYAN + "🎰 Nenhuma vitória registrada até o momento." + AnsiColors.RESET);
            voltarAoMenuDeOpcoes(scanner);
            return;
        }

        for (int i = 0; i < jogadoresPorVitorias.size(); i++) {
            String cor;
            if (i == 0) cor = AnsiColors.YELLOW;
            else if (i == 1) cor = AnsiColors.CYAN;
            else if (i == 2) cor = AnsiColors.GREEN;
            else cor = AnsiColors.PURPLE;

            System.out.println(cor + (i + 1) + "ª Posição: " + jogadoresPorVitorias.get(i).getNickname() + AnsiColors.RESET);
        }

        voltarAoMenuDeOpcoes(scanner);
    }

    private static void voltarAoMenuDeOpcoes(Scanner scanner) {
        System.out.print("\n" + AnsiColors.YELLOW + "🧭 Digite enter para voltar: " + AnsiColors.RESET);
        scanner.nextLine();
        System.out.println(AnsiColors.PURPLE + "🔙 Retornando..." + AnsiColors.RESET);
    }
}