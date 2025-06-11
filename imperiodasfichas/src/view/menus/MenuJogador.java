package view.menus;

import controller.GerenciadorJogador;
import controller.GerenciadorJogo;
import model.Jogador;
import view.ImperioDasFichas;
import view.utils.LimparTerminal;
import view.utils.AnsiColors;

import java.util.Scanner;

public class MenuJogador {

    public static void controleLogin(GerenciadorJogador gerenciadorJogador, GerenciadorJogo gerenciadorJogo) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n" + AnsiColors.PURPLE + "🔐 Império das Fichas - Sistema de Login" + AnsiColors.RESET);

        System.out.print(AnsiColors.CYAN + "🎲 Digite o seu nickname: " + AnsiColors.RESET);
        String nickname = scanner.nextLine();

        Jogador jogador = null;
        try {
            jogador = gerenciadorJogador.buscarJogador(nickname);
        } catch (Exception e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
            return;
        }

        System.out.printf(AnsiColors.GREEN + "✅ Login realizado com sucesso! Bem-vindo, %s!!\n" + AnsiColors.RESET, jogador.getNickname());
        LimparTerminal.executar();
        MenuOpcoes.executarMenu(jogador, gerenciadorJogador, gerenciadorJogo);
    }

    public static void menuEditarJogador(Jogador jogador, GerenciadorJogador gerenciadorJogador) {
        LimparTerminal.executar();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n" + AnsiColors.YELLOW + "✏️ Menu de Edição do Jogador" + AnsiColors.RESET);
            System.out.println(AnsiColors.CYAN + jogador + AnsiColors.RESET + "\n");

            System.out.println(AnsiColors.GREEN + "1. Editar Nome" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "2. Editar Idade" + AnsiColors.RESET);
            System.out.println(AnsiColors.RED + "3. Voltar" + AnsiColors.RESET);

            System.out.print("\n" + AnsiColors.YELLOW + "🧭 Escolha uma opção: " + AnsiColors.RESET);
            opcao = ImperioDasFichas.lerInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print(AnsiColors.CYAN + "Digite o novo nome: " + AnsiColors.RESET);
                    String novoNome = scanner.nextLine();
                    jogador.setNome(novoNome);
                    gerenciadorJogador.atualizarJogador(jogador, jogador.getNickname());
                    System.out.println(AnsiColors.GREEN + "✅ Nome atualizado com sucesso!" + AnsiColors.RESET);
                    break;

                case 2:
                    System.out.print(AnsiColors.CYAN + "Digite a nova idade: " + AnsiColors.RESET);
                    int novaIdade = ImperioDasFichas.lerInteiro(scanner.nextLine());
                    if (novaIdade < 18) {
                        System.out.println(AnsiColors.RED + "❌ Idade inválida! Apenas maiores de 18 anos." + AnsiColors.RESET);
                        break;
                    }
                    jogador.setIdade(novaIdade);
                    gerenciadorJogador.atualizarJogador(jogador, jogador.getNickname());
                    System.out.println(AnsiColors.GREEN + "✅ Idade atualizada com sucesso!" + AnsiColors.RESET);
                    break;

                case 3:
                    System.out.println(AnsiColors.PURPLE + "🔙 Retornando..." + AnsiColors.RESET);
                    break;

                default:
                    System.out.println(AnsiColors.RED + "⚠️ Opção inválida!" + AnsiColors.RESET);
                    break;
            }

        } while (opcao != 3);
    }

    public static boolean menuDeletarJogador(Jogador jogador, GerenciadorJogador gerenciadorJogador) {
        LimparTerminal.executar();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n" + AnsiColors.YELLOW + "🗑️️ Menu de Deletar Jogador(a)" + AnsiColors.RESET);

            System.out.println(AnsiColors.RED + "1. Remover Conta" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "2. Voltar" + AnsiColors.RESET);

            System.out.print("\n" + AnsiColors.YELLOW + "🧭 Escolha uma opção: " + AnsiColors.RESET);
            opcao = ImperioDasFichas.lerInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print(AnsiColors.CYAN + "Digite o seu nickname para confirmar a exclusão da conta: " + AnsiColors.RESET);
                    String nickname = scanner.nextLine();

                    if (!jogador.getNickname().equals(nickname)) {
                        System.out.println(AnsiColors.RED + "❌ Verifique o nickname digitado e tente novamente." + AnsiColors.RESET);
                        break;
                    }

                    try {
                        gerenciadorJogador.removerJogador(nickname);
                        System.out.println(AnsiColors.GREEN + "✅ Sua conta foi removida com sucesso!" + AnsiColors.RESET);
                        return true;
                    } catch (Exception e) {
                        System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
                        break;
                    }

                case 2:
                    System.out.println(AnsiColors.PURPLE + "🔙 Retornando..." + AnsiColors.RESET);
                    break;

                default:
                    System.out.println(AnsiColors.RED + "⚠️ Opção inválida!" + AnsiColors.RESET);
                    break;
            }

        } while (opcao != 2);

        return false;
    }
}