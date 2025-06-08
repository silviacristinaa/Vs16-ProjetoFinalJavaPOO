package view;

import controller.GerenciadorJogo;
import model.Jogador;
import model.jogos.Jogo;
import model.jogos.roletas.Roleta;

import java.util.ArrayList;
import java.util.Scanner;

public class ImperioDasFichas {

    static final GerenciadorJogo gerenciador = new GerenciadorJogo("Império das Fichas", 1.00, new ArrayList<>());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcao;
        boolean jogadorCadastrado = false;

        // Adição do jogo "Roleta Clássica" no Array de jogos
        Jogo roleta = new Roleta("Roleta Clássica", "Aposte em PAR (0) ou ÍMPAR (1). Se acertar, você ganha o dobro!");
        gerenciador.adicionarJogo(roleta);

        do {
            System.out.println("\n=======================================");
            System.out.println("🎰  BEM-VINDO AO IMPÉRIO DAS FICHAS!  🎰");
            System.out.println("=======================================\n");

            System.out.println("‍🎮  Menu Inicial\n");

            System.out.println("🆕  1. Cadastrar novo jogador");
            System.out.println("🔐  2. Realizar login");
            System.out.println("🚪  3. Desistir de tentar a sorte");

            System.out.print("\n🧭 Escolha uma opção: ");

            opcao = lerNumeroInteiro(scanner.nextLine());

            switch (opcao) {

                case 1:
                    System.out.println("Cadastrar jogador selecionado!");

                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Idade: ");
                    int idade = lerNumeroInteiro(scanner.nextLine());

                    if (idade < 18) {
                        System.out.println("Não é permitido jogadores menor de idade. Somente maior de 18 anos.");
                        break;
                    }

                    System.out.print("Nickname: ");
                    String nickname = scanner.nextLine();

                    if (!gerenciador.adicionarJogador(nome, idade, nickname)) {
                        System.out.println("Erro. Já existe um jogador com esse nickname.");
                        break;
                    }

                    Jogador jogadorNovo = gerenciador.buscarJogador(nickname);
                    menuOpcoesJogador(jogadorNovo);

                    break;
                case 2:
                    controleLogin();
                    break;
                case 3:
                    System.out.println("\n👋 Obrigado por jogar! Volte sempre!");
                    break;

                default:
                    System.out.println("\n⚠️  Opção inválida! Por favor, tente novamente.");
                    break;
            }

        } while (opcao != 3);

        scanner.close();
    }

    public static void menuCarteira(Jogador jogador) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.printf("\n💼 Carteira Virtual do Jogador %s\n", jogador.getNickname());

            System.out.println(jogador.getCarteira() + "\n");

            System.out.println("💰 1. Depositar dinheiro");
            System.out.println("💸 2. Sacar dinheiro");
            System.out.println("🎟️ 3. Comprar fichas");
            System.out.println("🎯 4. Vender fichas");
            System.out.println("🚪 5. Retornar");

            System.out.print("\n🧭 Escolha uma opção: ");
            opcao = lerNumeroInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("\n💰 Digite o valor do depósito: R$ ");
                    double deposito = Double.parseDouble(scanner.nextLine());

                    if (!jogador.getCarteira().depositarDinheiro(deposito)) {
                        System.out.println("❌ Valor inválido. Somente valores positivos.");
                    } else {
                        System.out.println("✅ Valor de R$ " + deposito + " depositado com sucesso!");
                    }
                    break;

                case 2:
                    System.out.print("\n💸 Digite o valor que deseja sacar: R$ ");
                    double saque = Double.parseDouble(scanner.nextLine());

                    if (saque <= 0) {
                        System.out.println("❌ Valor inválido! Somente valores positivos.");
                        break;
                    }

                    if (!jogador.getCarteira().sacarDinheiro(saque)) {
                        System.out.println("❌ Valor inválido. Saldo insuficiente.");
                    } else {
                        System.out.println("✅ Valor de R$ " + saque + " sacado com sucesso!");
                    }
                    break;

                case 3:
                    System.out.print("\n🎟️  Digite o número de fichas que deseja comprar: ");
                    int qtdComprar = lerNumeroInteiro(scanner.nextLine());

                    if (qtdComprar <= 0) {
                        System.out.println("❌ Valor inválido! Somente valores positivos.");
                        break;
                    }

                    if (!gerenciador.comprarFicha(jogador.getNickname(), qtdComprar)) {
                        System.out.println("❌ Dinheiro insuficiente para comprar as fichas!");
                    } else {
                        System.out.println("✅ Compra de " + qtdComprar + " fichas realizada com sucesso!");
                    }
                    break;

                case 4:
                    System.out.print("\n🎯 Digite o número de fichas que deseja vender: ");
                    int qtdVender = lerNumeroInteiro(scanner.nextLine());

                    if (qtdVender <= 0) {
                        System.out.println("❌ Valor inválido! Somente valores positivos.");
                        break;
                    }

                    if (!gerenciador.venderFicha(jogador.getNickname(), qtdVender)) {
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

    public static void menuOpcoesJogador(Jogador jogador) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n O QUE DESEJA FAZER?\n");
            System.out.println("💼 1. Acessar Carteira");
            System.out.println("🎰 2. Jogar Roleta");
            System.out.println("🚪 3. Voltar ao Menu Inicial...");
            System.out.print("\n🧭 Escolha uma opção: ");
            opcao = lerNumeroInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    menuCarteira(jogador);
                    break;
                case 2:
                    menuRoleta(jogador);
                    break;
                case 3:
                    System.out.println("\n👋 Retornando ao Menu Principal...");
                    break;
                default:
                    System.out.println("\n⚠️  Opção inválida! Por favor, tente novamente.");
                    break;
            }
        } while (opcao != 3);
    }

    public static void controleLogin() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🔐 Império das Fichas - Sistema de Login");

        System.out.print("Digite seu nickname: ");
        String nickname = scanner.nextLine();

        Jogador jogador = gerenciador.buscarJogador(nickname);

        if (jogador == null) {
            System.out.println("❌ Jogador não encontrado. Verifique o nickname ou cadastre-se primeiro.");
            return;
        }

        System.out.printf("✅ Login realizado com sucesso! Bem-vindo, %s!!\n", jogador.getNickname());
        menuOpcoesJogador(jogador);
    }

    public static void menuRoleta(Jogador jogador) {
        Scanner scanner = new Scanner(System.in);

        Jogo roleta = gerenciador.buscarJogo("Roleta Clássica");

        if (roleta == null) {
            System.out.println("❌ Roleta não está disponível no momento.");
            return;
        }

        System.out.println("\n🎰 Bem-vindo à Roleta Clássica!");
        System.out.println("\n==========================================================================================");
        roleta.exibirRegras();
        System.out.println("==========================================================================================\n");
        System.out.printf("🎟️ Você tem %d fichas.\n", jogador.getCarteira().getFichas());
        System.out.print("Quantas fichas deseja apostar?\n");
        System.out.print("\nFICHAS APOSTADAS: ");
        int valorApostado = lerNumeroInteiro(scanner.nextLine());

        if (valorApostado <= 0 || valorApostado < roleta.getValorInicial()) {
            System.out.println("\n=======================================================");
            System.out.printf("❌ Aposta inválida. Mínimo: %d fichas.\n", roleta.getValorInicial());
            System.out.println("=======================================================");
            return;
        }
        if (valorApostado > jogador.getCarteira().getFichas()) {
            System.out.println("❌ Você não tem fichas suficientes para essa aposta.");
            return;
        }

        System.out.println("\nEscolha sua aposta:");
        System.out.println("Digite '0' para escolher PAR");
        System.out.println("Digite '1' para escolher ÍMPAR");
        System.out.print("\nSUA ESCOLHA: ");
        int escolha = lerNumeroInteiro(scanner.nextLine());

        if (!roleta.apostaValida(valorApostado, escolha)) {
            System.out.println("❌ Aposta cancelada.");
            return;
        }

        jogador.getCarteira().sacarFichas(valorApostado);

        boolean ganhou = roleta.jogar(valorApostado, escolha);

        if (ganhou) {
            int premio = valorApostado * 2;
            jogador.getCarteira().depositarFichas(premio);
        }
    }

    private static int lerNumeroInteiro(String textoUsuario) {
        if (ehNumero(textoUsuario)) {
            return Integer.parseInt(textoUsuario);
        }
        return -1;
    }

    private static boolean ehNumero(String texto) {
        return texto.matches("-?\\d+");
    }
}
