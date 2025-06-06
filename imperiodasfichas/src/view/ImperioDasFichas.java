package view;

import controller.GerenciadorJogo;
import model.Jogador;

import java.util.ArrayList;
import java.util.Scanner;

public class ImperioDasFichas {

    static final GerenciadorJogo gerenciador = new GerenciadorJogo("Império das Fichas", 1.00, new ArrayList<>());

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcao;
        boolean jogadorCadastrado = false;

        do {
            System.out.println("\n=======================================");
            System.out.println("🎰  BEM-VINDO AO IMPÉRIO DAS FICHAS!  🎰");
            System.out.println("=======================================\n");

            System.out.println("‍🎮  Menu jogador");

            System.out.println("🆕  1. Cadastrar novo jogador");
            // implementar realização de login
            //System.out.println("🔐  2. Realizar login");

            System.out.println("\n🚪  2. Desistir de tentar a sorte");

            System.out.print("\n🧭 Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());


            switch (opcao) {

                case 1:
                    System.out.println("Cadastrar jogador selecionado!");

                    System.out.print("Nome:");
                    String nome = scanner.nextLine();

                    System.out.print("Idade:");
                    int idade = Integer.parseInt(scanner.nextLine());

                    if (idade < 18) {
                        System.out.println("Não é permitido jogadores menor de idade. Somente maior de 18 anos.");
                        break;
                    }

                    System.out.print("Nickname:");
                    String nickname = scanner.nextLine();

                    if (!gerenciador.adicionarJogador(nome, idade, nickname)) {
                        System.out.println("Erro. Já existe um jogador com esse nickname.");
                        break;
                    }

                    menuCarteira();

                    break;

                default:
                    System.out.println("\n⚠️  Opção inválida! Por favor, tente novamente.");
                    break;
            }

        } while (opcao != 2);

        scanner.close();
    }

    public static void menuCarteira() {

        Scanner scanner = new Scanner(System.in);
        int opcao;
        String nickname;
        Jogador jogador;

        do {
            System.out.println("💼  Carteira Virtual");
            System.out.println("💰  1. Depositar dinheiro");
            System.out.println("💸  2. Sacar dinheiro");
            System.out.println("🎟️  3. Depositar fichas");
            System.out.println("🎯  4. Sacar fichas");

            System.out.println("\n🚪  5. Retornar");

            System.out.print("\n🧭 Escolha uma opção: ");

            opcao = Integer.parseInt(scanner.nextLine());

            System.out.println("Digite o nickname: ");
            nickname = scanner.nextLine();

            jogador = gerenciador.buscarJogador(nickname);

            if (jogador == null) {
                System.out.println("Jogador não encontrado.");
                break;
            }

            switch (opcao) {

                case 1:
                    System.out.print("\n💰 Digite o valor do depósito: R$ ");
                    double deposito = Double.parseDouble(scanner.nextLine());

                    if (!jogador.getCarteira().depositarDinheiro(deposito)) {
                        System.out.println("Valor inválido. Somente valores positivos.");
                        break;
                    }

                    System.out.println("✅ Valor de R$ " + deposito + " depositado com sucesso!");
                    break;

                case 2:
                    System.out.print("\n💸 Digite o valor que deseja sacar: R$ ");
                    double saque = Double.parseDouble(scanner.nextLine());

                    if (!jogador.getCarteira().sacarDinheiro(saque)) {
                        System.out.println("Valor inválido. Saldo insuficiente.");
                        break;
                    }

                    System.out.println("✅ Valor de R$ " + saque + " sacado com sucesso!");
                    break;

                case 3:
                    System.out.println("\n🎟️  Comprar fichas selecionado!");

                    // lógica do depósito de fichas

                    break;

                case 4:
                    System.out.println("\n🎯 Vender fichas selecionado!");

                    // lógica do saque de fichas

                    break;

                case 5:
                    System.out.println("\n👋 Obrigado por jogar no Império das Fichas e tentar a sua sorte. Até logo!");
                    break;

                default:
                    System.out.println("\n⚠️  Opção inválida! Por favor, tente novamente.");
                    break;
            }

        } while (opcao != 5);
    }
}
