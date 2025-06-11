package view.menus;

import view.utils.LimparTerminal;
import view.utils.AnsiColors;

import controller.GerenciadorJogador;
import controller.GerenciadorJogo;
import model.Jogador;
import view.ImperioDasFichas;

import java.util.Scanner;

public class MenuCarteira {

    public static void executarMenu(Jogador jogador, GerenciadorJogador gerenciadorJogador, GerenciadorJogo gerenciadorJogo) {
        LimparTerminal.executar();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.printf("\n" + AnsiColors.YELLOW + "💼 Carteira Virtual - Jogador(a) %s" + AnsiColors.RESET + "\n", jogador.getNickname());

            System.out.println(AnsiColors.CYAN + jogador.getCarteira() + AnsiColors.RESET + "\n");

            System.out.println(AnsiColors.GREEN + "💰 1. Depositar dinheiro" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "💸 2. Sacar dinheiro" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "🎟️ 3. Comprar fichas" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "🎯 4. Vender fichas" + AnsiColors.RESET);
            System.out.println(AnsiColors.RED + "🚪 5. Retornar" + AnsiColors.RESET);

            System.out.print("\n" + AnsiColors.YELLOW + "🧭 Escolha uma opção: " + AnsiColors.RESET);
            opcao = ImperioDasFichas.lerInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("\n" + AnsiColors.GREEN + "💰 Digite o valor do depósito: R$ " + AnsiColors.RESET);
                    double deposito = ImperioDasFichas.lerDouble(scanner.nextLine());

                    try {
                        gerenciadorJogador.fazerDeposito(jogador.getNickname(), deposito);
                        System.out.println(AnsiColors.GREEN + "✅ Valor de R$ " + deposito + " depositado com sucesso!" + AnsiColors.RESET);
                    } catch (Exception e) {
                        System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
                    }

                    break;

                case 2:
                    System.out.print("\n" + AnsiColors.GREEN + "💸 Digite o valor que deseja sacar: R$ " + AnsiColors.RESET);
                    double saque = ImperioDasFichas.lerDouble(scanner.nextLine());

                    if (saque <= 0) {
                        System.out.println(AnsiColors.RED + "❌ Valor inválido! Somente valores positivos." + AnsiColors.RESET);
                        break;
                    }

                    try {
                        gerenciadorJogador.fazerSaque(jogador.getNickname(), saque);
                        System.out.println(AnsiColors.GREEN + "✅ Valor de R$ " + saque + " sacado com sucesso!" + AnsiColors.RESET);
                    } catch (Exception e) {
                        System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
                    }

                    break;

                case 3:
                    System.out.print("\n" + AnsiColors.GREEN + "🎟️  Digite o número de fichas que deseja comprar: " + AnsiColors.RESET);
                    int qtdComprar = ImperioDasFichas.lerInteiro(scanner.nextLine());

                    if (qtdComprar <= 0) {
                        System.out.println(AnsiColors.RED + "❌ Valor inválido! Somente valores positivos." + AnsiColors.RESET);
                        break;
                    }

                    try {
                        gerenciadorJogo.comprarFicha(jogador.getNickname(), qtdComprar);
                        System.out.println(AnsiColors.GREEN + "✅ Compra de " + qtdComprar + " fichas realizada com sucesso!" + AnsiColors.RESET);
                    } catch (Exception e) {
                        System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
                    }

                    break;

                case 4:
                    System.out.print("\n" + AnsiColors.GREEN + "🎯 Digite o número de fichas que deseja vender: " + AnsiColors.RESET);
                    int qtdVender = ImperioDasFichas.lerInteiro(scanner.nextLine());

                    if (qtdVender <= 0) {
                        System.out.println(AnsiColors.RED + "❌ Valor inválido! Somente valores positivos." + AnsiColors.RESET);
                        break;
                    }

                    try {
                        gerenciadorJogo.venderFicha(jogador.getNickname(), qtdVender);
                        System.out.println(AnsiColors.GREEN + "✅ Venda de " + qtdVender + " fichas realizada com sucesso!" + AnsiColors.RESET);
                    } catch (Exception e) {
                        System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
                    }

                    break;

                case 5:
                    System.out.println("\n" + AnsiColors.PURPLE + "👋 Voltando ao Menu de Opções do Jogador..." + AnsiColors.RESET);
                    LimparTerminal.executar();
                    break;

                default:
                    System.out.println("\n" + AnsiColors.RED + "⚠️  Opção inválida! Por favor, tente novamente." + AnsiColors.RESET);
                    break;
            }
        } while (opcao != 5);
    }
}