package view.menus;

import controller.GerenciadorJogador;
import controller.GerenciadorJogo;
import exceptions.RegraDeNegocioException;
import model.Jogador;
import view.ImperioDasFichas;
import view.utils.LimparTerminal;
import view.utils.AnsiColors;
import java.util.Scanner;

public class MenuInicial {

    public static void executarMenu(GerenciadorJogador gerenciadorJogador, GerenciadorJogo gerenciadorJogo) throws RegraDeNegocioException {
        Scanner scanner = new Scanner(System.in);

        int opcao;
        Jogador jogador = null;

        do {
            LimparTerminal.executar();

            System.out.println("\n" + AnsiColors.YELLOW + "========================================" + AnsiColors.RESET);
            System.out.println(AnsiColors.PURPLE + "🎰  BEM-VINDO AO IMPÉRIO DAS FICHAS!  🎰" + AnsiColors.RESET);
            System.out.println(AnsiColors.YELLOW + "========================================" + AnsiColors.RESET + "\n");

            System.out.println(AnsiColors.CYAN + "╭───────────────────────────────╮" + AnsiColors.RESET);
            System.out.println(AnsiColors.CYAN + "       ‍🎮  MENU INICIAL 🎮         " + AnsiColors.RESET);
            System.out.println(AnsiColors.CYAN + "╰───────────────────────────────╯\n" + AnsiColors.RESET);

            System.out.println(AnsiColors.GREEN + "  🆕  1. Cadastrar novo jogador" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "  🔐  2. Realizar login" + AnsiColors.RESET);
            System.out.println(AnsiColors.RED + "  🚪  3. Desistir de tentar a sorte" + AnsiColors.RESET);

            System.out.print("\n" + AnsiColors.YELLOW + "🧭 Escolha uma opção: " + AnsiColors.RESET);

            opcao = ImperioDasFichas.lerInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.println("\n" + AnsiColors.GREEN + "╔══════════════════════════════════════╗" + AnsiColors.RESET);
                    System.out.println(AnsiColors.GREEN + "   ✅ Cadastro de jogador iniciado!   " + AnsiColors.RESET);
                    System.out.println(AnsiColors.GREEN + "╚══════════════════════════════════════╝\n" + AnsiColors.RESET);

                    System.out.print("👤 Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("🗓️ Idade: ");
                    int idade = ImperioDasFichas.lerInteiro(scanner.nextLine());

                    if (idade < 18) {
                        System.out.println("\n" + AnsiColors.RED + "⚠️  Entrada proibida: apenas maiores de 18 anos podem jogar no Império das Fichas." + AnsiColors.RESET);
                        break;
                    }

                    System.out.print("🎲 Nickname: ");
                    String nickname = scanner.nextLine();

                    try {
                        jogador = gerenciadorJogador.adicionarJogador(nome, idade, nickname);
                    } catch (Exception e) {
                        System.out.println("\n" + AnsiColors.RED + "❌ " + e.getMessage() + AnsiColors.RESET);
                        break;
                    }

                    LimparTerminal.executar();
                    MenuOpcoes.executarMenu(jogador, gerenciadorJogador, gerenciadorJogo);
                    break;

                case 2:
                    MenuJogador.controleLogin(gerenciadorJogador, gerenciadorJogo);
                    break;

                case 3:
                    System.out.println("\n" + AnsiColors.PURPLE + "👋 Obrigado(a) por jogar! Volte sempre!" + AnsiColors.RESET);
                    break;

                default:
                    System.out.println("\n" + AnsiColors.RED + "⚠️  Opção inválida! Por favor, tente novamente." + AnsiColors.RESET);
                    break;
            }

        } while (opcao != 3);
    }
}