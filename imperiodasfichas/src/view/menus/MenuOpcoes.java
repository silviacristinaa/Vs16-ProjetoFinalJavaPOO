package view.menus;

import controller.GerenciadorJogador;
import controller.GerenciadorJogo;
import model.Jogador;
import view.ImperioDasFichas;
import view.utils.LimparTerminal;
import view.utils.AnsiColors;

import java.util.Scanner;

public class MenuOpcoes {

    public static void executarMenu(Jogador jogador, GerenciadorJogador gerenciadorJogador, GerenciadorJogo gerenciadorJogo) {

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n" + AnsiColors.YELLOW + "✨ O QUE DESEJA FAZER? ✨" + AnsiColors.RESET + "\n");
            System.out.println(AnsiColors.GREEN + "💼 1. Acessar Carteira" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "🎰 2. Jogar Roleta" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "🎰 3. Jogar Caça Níquel" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "🃏 4. Jogar BlackJack" + AnsiColors.RESET);
            System.out.println(AnsiColors.CYAN + "🏆 5. Ranking" + AnsiColors.RESET);
            System.out.println(AnsiColors.CYAN + "✏️ 6. Editar Perfil" + AnsiColors.RESET);
            System.out.println(AnsiColors.RED + "🗑️ 7. Excluir Conta" + AnsiColors.RESET);
            System.out.println(AnsiColors.PURPLE + "🚪 8. Voltar ao Menu Inicial..." + AnsiColors.RESET);

            System.out.print("\n" + AnsiColors.YELLOW + "🧭 Escolha uma opção: " + AnsiColors.RESET);
            opcao = ImperioDasFichas.lerInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    MenuCarteira.executarMenu(jogador, gerenciadorJogador, gerenciadorJogo);
                    break;
                case 2:
                    MenuJogos.executarMenu(jogador, gerenciadorJogo);
                    break;
                case 3:
                    MenuJogos.menuCacaNiquel(jogador, gerenciadorJogo);
                    break;
                case 4:
                    MenuJogos.menuBlackJack(jogador, gerenciadorJogo);
                    break;
                case 5:
                    MenuRanking.executarMenu(gerenciadorJogador);
                    break;
                case 6:
                    MenuJogador.menuEditarJogador(jogador, gerenciadorJogador);
                    break;
                case 7:
                    boolean deletou = MenuJogador.menuDeletarJogador(jogador, gerenciadorJogador);

                    if (deletou) {
                        opcao = 8;
                    }
                    break;
                case 8:
                    System.out.println("\n" + AnsiColors.PURPLE + "👋 Retornando ao Menu Principal..." + AnsiColors.RESET);
                    break;
                default:
                    System.out.println("\n" + AnsiColors.RED + "⚠️  Opção inválida! Por favor, tente novamente." + AnsiColors.RESET);
                    break;
            }

        } while (opcao != 8);
    }
}