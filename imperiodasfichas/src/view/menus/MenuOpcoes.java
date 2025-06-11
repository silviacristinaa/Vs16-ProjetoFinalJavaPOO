package view.menus;

import controller.GerenciadorJogador;
import controller.GerenciadorJogo;
import model.Jogador;
import view.ImperioDasFichas;

import java.util.Scanner;

public class MenuOpcoes {

    public static void executarMenu(Jogador jogador, GerenciadorJogador gerenciadorJogador, GerenciadorJogo gerenciadorJogo) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n ✨ O QUE DESEJA FAZER? ✨\n");
            System.out.println("💼 1. Acessar Carteira");
            System.out.println("🎰 2. Jogar Roleta");
            System.out.println("🎰 3. Jogar Caça Níquel");
            System.out.println("🃏 4. Jogar BlackJack");
            System.out.println("✏️ 5. Editar Perfil");
            System.out.println("🗑️ 6. Excluir Conta");
            System.out.println("🚪 7. Voltar ao Menu Inicial...");

            System.out.print("\n🧭 Escolha uma opção: ");
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
                    MenuJogador.menuEditarJogador(jogador, gerenciadorJogador);
                    break;
                case 6:
                    boolean deletou = MenuJogador.menuDeletarJogador(jogador, gerenciadorJogador);

                    if (deletou) {
                        opcao = 7;
                    }
                    break;
                case 7:
                    System.out.println("\n👋 Retornando ao Menu Principal...");
                    break;
                default:
                    System.out.println("\n⚠️  Opção inválida! Por favor, tente novamente.");
                    break;
            }

        } while (opcao != 7);
    }
}
