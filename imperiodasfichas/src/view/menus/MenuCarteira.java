package view.menus;

import view.utils.LimparTerminal;
import view.utils.AnsiColors;

import controller.GerenciadorJogador;
import controller.GerenciadorJogo;
import exceptions.NaoEncontradoException;
import exceptions.RegraDeNegocioException;
import model.Jogador;
import view.ImperioDasFichas;

import java.util.Scanner;

public class MenuCarteira {

    public static void executarMenu(Jogador jogador, GerenciadorJogador gerenciadorJogador, GerenciadorJogo gerenciadorJogo) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            LimparTerminal.executar(); // Limpa o terminal antes de cada exibição do menu

            // **IMPORTANTE:** Recarrega o jogador do banco de dados a cada iteração do loop
            // para garantir que os dados da carteira estejam sempre atualizados.
            try {
                jogador = gerenciadorJogador.buscarJogador(jogador.getNickname());
            } catch (NaoEncontradoException | RegraDeNegocioException e) {
                System.out.println(AnsiColors.RED + "❌ Erro ao carregar dados do jogador: " + e.getMessage() + AnsiColors.RESET);
                // Em caso de erro ao buscar, talvez seja melhor sair do menu ou tentar novamente
                break;
            }

            System.out.printf("\n" + AnsiColors.YELLOW + "💼 Carteira Virtual - Jogador(a) %s" + AnsiColors.RESET + "\n", jogador.getNickname());

            // Exibe a carteira atualizada
            System.out.println(AnsiColors.CYAN + jogador.getCarteira() + AnsiColors.RESET + "\n");

            System.out.println(AnsiColors.GREEN + "💰 1. Depositar dinheiro" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "💸 2. Sacar dinheiro" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "🎟️ 3. Comprar fichas" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "🎯 4. Vender fichas" + AnsiColors.RESET);
            System.out.println(AnsiColors.RED + "🚪 5. Retornar" + AnsiColors.RESET);

            System.out.print("\n" + AnsiColors.YELLOW + "🧭 Escolha uma opção: " + AnsiColors.RESET);
            opcao = ImperioDasFichas.lerInteiro(scanner.nextLine());

            try { // Bloco try-catch para as operações que podem lançar exceções
                switch (opcao) {
                    case 1:
                        System.out.print("\n" + AnsiColors.GREEN + "💰 Digite o valor do depósito: R$ " + AnsiColors.RESET);
                        double deposito = ImperioDasFichas.lerDouble(scanner.nextLine());
                        gerenciadorJogador.fazerDeposito(jogador.getNickname(), deposito);
                        System.out.println(AnsiColors.GREEN + "✅ Valor de R$ " + String.format("%.2f", deposito) + " depositado com sucesso!" + AnsiColors.RESET);
                        break;

                    case 2:
                        System.out.print("\n" + AnsiColors.GREEN + "💸 Digite o valor que deseja sacar: R$ " + AnsiColors.RESET);
                        double saque = ImperioDasFichas.lerDouble(scanner.nextLine());
                        if (saque <= 0) {
                            System.out.println(AnsiColors.RED + "❌ Valor inválido! Somente valores positivos." + AnsiColors.RESET);
                            break;
                        }
                        gerenciadorJogador.fazerSaque(jogador.getNickname(), saque);
                        System.out.println(AnsiColors.GREEN + "✅ Valor de R$ " + String.format("%.2f", saque) + " sacado com sucesso!" + AnsiColors.RESET);
                        break;

                    case 3:
                        System.out.print("\n" + AnsiColors.GREEN + "🎟️  Digite o número de fichas que deseja comprar: " + AnsiColors.RESET);
                        int qtdComprar = ImperioDasFichas.lerInteiro(scanner.nextLine());
                        if (qtdComprar <= 0) {
                            System.out.println(AnsiColors.RED + "❌ Valor inválido! Somente valores positivos." + AnsiColors.RESET);
                            break;
                        }
                        gerenciadorJogo.comprarFicha(jogador.getNickname(), qtdComprar);
                        System.out.println(AnsiColors.GREEN + "✅ Compra de " + qtdComprar + " fichas realizada com sucesso!" + AnsiColors.RESET);
                        break;

                    case 4:
                        System.out.print("\n" + AnsiColors.GREEN + "🎯 Digite o número de fichas que deseja vender: " + AnsiColors.RESET);
                        int qtdVender = ImperioDasFichas.lerInteiro(scanner.nextLine());
                        if (qtdVender <= 0) {
                            System.out.println(AnsiColors.RED + "❌ Valor inválido! Somente valores positivos." + AnsiColors.RESET);
                            break;
                        }
                        gerenciadorJogo.venderFicha(jogador.getNickname(), qtdVender);
                        System.out.println(AnsiColors.GREEN + "✅ Venda de " + qtdVender + " fichas realizada com sucesso!" + AnsiColors.RESET);
                        break;

                    case 5:
                        System.out.println("\n" + AnsiColors.PURPLE + "👋 Voltando ao Menu de Opções do Jogador..." + AnsiColors.RESET);
                        LimparTerminal.executar();
                        break;

                    default:
                        System.out.println("\n" + AnsiColors.RED + "⚠️  Opção inválida! Por favor, tente novamente." + AnsiColors.RESET);
                        break;
                }
            } catch (Exception e) { // Captura exceções dos métodos do gerenciador
                System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
            }

            // Pausa para o usuário ver a mensagem antes de limpar a tela novamente
            if (opcao != 5) {
                System.out.print(AnsiColors.YELLOW + "\nPressione Enter para continuar..." + AnsiColors.RESET);
                scanner.nextLine();
            }

        } while (opcao != 5);
    }
}