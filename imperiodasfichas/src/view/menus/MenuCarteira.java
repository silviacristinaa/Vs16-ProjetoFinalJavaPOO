package view.menus;

import controller.GerenciadorJogador;
import controller.GerenciadorJogo;
import model.Jogador;
import view.ImperioDasFichas;

import java.util.Scanner;

public class MenuCarteira {

    public static void executarMenu(Jogador jogador, GerenciadorJogador gerenciadorJogador, GerenciadorJogo gerenciadorJogo) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.printf("\n💼 Carteira Virtual - Jogador(a) %s\n", jogador.getNickname());

            System.out.println(jogador.getCarteira() + "\n");

            System.out.println("💰 1. Depositar dinheiro");
            System.out.println("💸 2. Sacar dinheiro");
            System.out.println("🎟️ 3. Comprar fichas");
            System.out.println("🎯 4. Vender fichas");
            System.out.println("🚪 5. Retornar");

            System.out.print("\n🧭 Escolha uma opção: ");
            opcao = ImperioDasFichas.lerInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("\n💰 Digite o valor do depósito: R$ ");
                    double deposito = ImperioDasFichas.lerDouble(scanner.nextLine());

                    if (!gerenciadorJogador.fazerDeposito(jogador.getNickname(), deposito)) {
                        System.out.println("❌ Valor inválido. Somente valores positivos.");
                    } else {
                        System.out.println("✅ Valor de R$ " + deposito + " depositado com sucesso!");
                    }
                    break;

                case 2:
                    System.out.print("\n💸 Digite o valor que deseja sacar: R$ ");
                    double saque = ImperioDasFichas.lerDouble(scanner.nextLine());

                    if (saque <= 0) {
                        System.out.println("❌ Valor inválido! Somente valores positivos.");
                        break;
                    }

                    if (!gerenciadorJogador.fazerSaque(jogador.getNickname(), saque)) {
                        System.out.println("❌ Valor inválido. Saldo insuficiente.");
                    } else {
                        System.out.println("✅ Valor de R$ " + saque + " sacado com sucesso!");
                    }
                    break;

                case 3:
                    System.out.print("\n🎟️  Digite o número de fichas que deseja comprar: ");
                    int qtdComprar = ImperioDasFichas.lerInteiro(scanner.nextLine());

                    if (qtdComprar <= 0) {
                        System.out.println("❌ Valor inválido! Somente valores positivos.");
                        break;
                    }

                    if (!gerenciadorJogo.comprarFicha(jogador.getNickname(), qtdComprar)) {
                        System.out.println("❌ Dinheiro insuficiente para comprar as fichas!");
                    } else {
                        System.out.println("✅ Compra de " + qtdComprar + " fichas realizada com sucesso!");
                    }
                    break;

                case 4:
                    System.out.print("\n🎯 Digite o número de fichas que deseja vender: ");
                    int qtdVender = ImperioDasFichas.lerInteiro(scanner.nextLine());

                    if (qtdVender <= 0) {
                        System.out.println("❌ Valor inválido! Somente valores positivos.");
                        break;
                    }

                    if (!gerenciadorJogo.venderFicha(jogador.getNickname(), qtdVender)) {
                        System.out.println("❌ Não foi possível vender as fichas.");
                    } else {
                        System.out.println("✅ Venda de " + qtdVender + " fichas realizada com sucesso!");
                    }
                    break;

                case 5:
                    System.out.println("\n👋 Voltando ao Menu de Opções do Jogador...");
                    break;

                default:
                    System.out.println("\n⚠️  Opção inválida! Por favor, tente novamente.");
                    break;
            }
        } while (opcao != 5);
    }
}
