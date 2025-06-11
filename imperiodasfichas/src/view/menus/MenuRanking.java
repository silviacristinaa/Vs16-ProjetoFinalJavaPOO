package view.menus;

import controller.GerenciadorJogador;
import model.Jogador;

import java.util.List;
import java.util.Scanner;

public class MenuRanking {

    public static void executarMenu(GerenciadorJogador gerenciadorJogador) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🏆 === TOP JOGADORES === 🏆\n");
        List<Jogador> jogadoresPorVitorias = gerenciadorJogador.listarJogadoresPorVitorias();

        if (jogadoresPorVitorias.isEmpty()) {
            System.out.println("🎰 Nenhuma vitória registrada até o momento.");
            voltarAoMenuDeOpcoes(scanner);
            return;
        }

        for (int i = 0; i < jogadoresPorVitorias.size(); i++) {
            System.out.println((i + 1) + "ª Posição: " + jogadoresPorVitorias.get(i).getNickname());
        }

        voltarAoMenuDeOpcoes(scanner);
    }

    private static void voltarAoMenuDeOpcoes(Scanner scanner) {
        System.out.print("\n🧭 Digite enter para voltar: ");
        scanner.nextLine();
        System.out.println("🔙 Retornando...");
    }
}
