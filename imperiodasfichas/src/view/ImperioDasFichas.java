package view;

import controller.GerenciadorJogador;
import controller.GerenciadorJogo;
import model.Jogador;
import model.jogos.Jogo;
import model.jogos.cacaniquel.CacaNiquel;
import model.jogos.roletas.Roleta;
import model.jogos.roletas.RoletaCores;

import java.util.ArrayList;
import java.util.Scanner;

public class ImperioDasFichas {

    static final GerenciadorJogador gerenciadorJogador = new GerenciadorJogador();
    static final GerenciadorJogo gerenciadorJogo = new GerenciadorJogo("Império das Fichas", 1.00, gerenciadorJogador);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcao;
        Jogador jogador = null;

        // Adição dos jogos de roleta
        Jogo roleta = new Roleta("Roleta Clássica", "Aposte em PAR (0) ou ÍMPAR (1). Se acertar, você ganha o dobro!");
        Jogo roletaCores = new RoletaCores("Roleta das Cores", "Aposte em uma cor: VERMELHO (0), AZUL (1), AMARELO (2), VERDE (3). Se acertar, ganha 4x o valor apostado!");
        Jogo cacaNiquel = new CacaNiquel("Caça Níquel", "Aperte a alavanca. Se acertar, ganhe o dobro do valor apostado!");

        gerenciadorJogo.adicionarJogo(roleta);
        gerenciadorJogo.adicionarJogo(roletaCores);
        gerenciadorJogo.adicionarJogo(cacaNiquel);

        do {
            System.out.println("\n=======================================");
            System.out.println("🎰  BEM-VINDO AO IMPÉRIO DAS FICHAS!  🎰");
            System.out.println("=======================================\n");

            System.out.println("‍🎮  Menu Inicial\n");

            System.out.println("🆕  1. Cadastrar novo jogador");
            System.out.println("🔐  2. Realizar login");
            System.out.println("🚪  3. Desistir de tentar a sorte");

            System.out.print("\n🧭 Escolha uma opção: ");

            opcao = lerInteiro(scanner.nextLine());

            switch (opcao) {

                case 1:
                    System.out.println("Cadastrar jogador selecionado!");

                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Idade: ");
                    int idade = lerInteiro(scanner.nextLine());

                    if (idade < 18) {
                        System.out.println("Não é permitido jogadores menor de idade. Somente maior de 18 anos.");
                        break;
                    }

                    System.out.print("Nickname: ");
                    String nickname = scanner.nextLine();
                    jogador = gerenciadorJogador.adicionarJogador(nome, idade, nickname);

                    if (jogador == null) {
                        System.out.println("Erro. Já existe um jogador com esse nickname.");
                        break;
                    }

                    menuOpcoesJogador(jogador);

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
            opcao = lerInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("\n💰 Digite o valor do depósito: R$ ");
                    double deposito = lerDouble(scanner.nextLine());

                    if (!gerenciadorJogador.fazerDeposito(jogador.getNickname(), deposito)) {
                        System.out.println("❌ Valor inválido. Somente valores positivos.");
                    } else {
                        System.out.println("✅ Valor de R$ " + deposito + " depositado com sucesso!");
                    }
                    break;

                case 2:
                    System.out.print("\n💸 Digite o valor que deseja sacar: R$ ");
                    double saque = lerDouble(scanner.nextLine());

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
                    int qtdComprar = lerInteiro(scanner.nextLine());

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
                    int qtdVender = lerInteiro(scanner.nextLine());

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

    public static void menuOpcoesJogador(Jogador jogador) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n O QUE DESEJA FAZER?\n");
            System.out.println("💼 1. Acessar Carteira");
            System.out.println("🎰 2. Jogar Roleta");
            System.out.println("🎰 3. Jogar Caça Níquel");
            System.out.println("🚪 4. Voltar ao Menu Inicial...");
            System.out.print("\n🧭 Escolha uma opção: ");
            opcao = lerInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    menuCarteira(jogador);
                    break;
                case 2:
                    menuJogos(jogador);
                    break;
                case 3:
                    menuCacaNiquel(jogador);
                    break;
                case 4:
                    System.out.println("\n👋 Retornando ao Menu Principal...");
                    break;
                default:
                    System.out.println("\n⚠️  Opção inválida! Por favor, tente novamente.");
                    break;
            }
        } while (opcao != 4);
    }

    public static void controleLogin() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🔐 Império das Fichas - Sistema de Login");

        System.out.print("Digite seu nickname: ");
        String nickname = scanner.nextLine();

        Jogador jogador = gerenciadorJogador.buscarJogador(nickname);

        if (jogador == null) {
            System.out.println("❌ Verifique o nickname ou cadastre-se primeiro.");
            return;
        }

        System.out.printf("✅ Login realizado com sucesso! Bem-vindo, %s!!\n", jogador.getNickname());
        menuOpcoesJogador(jogador);
    }

    public static void menuJogos(Jogador jogador) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🎰 Escolha o tipo de Roleta:");
        System.out.println("1. Roleta Clássica (Par/Ímpar)");
        System.out.println("2. Roleta das Cores");
        System.out.print("\n🧭 Escolha uma opção: ");
        int opcaoEscolhida = lerInteiro(scanner.nextLine());
        Jogo jogoSelecionado = null;

        switch (opcaoEscolhida) {
            case 1:
                jogoSelecionado = gerenciadorJogo.buscarJogo("Roleta Clássica");
                break;
            case 2:
                jogoSelecionado = gerenciadorJogo.buscarJogo("Roleta das Cores");
                break;
            default:
                System.out.println("❌ Opção inválida.");
                break;
        }

        if (jogoSelecionado == null) {
            System.out.println("❌ Roleta não está disponível no momento.");
            return;
        }

        System.out.println("\n🎰 Bem-vindo à " + jogoSelecionado.getNomeJogo() + "!");
        System.out.println("\n==========================================================================================");
        jogoSelecionado.exibirRegras();
        System.out.println("==========================================================================================\n");
        System.out.printf("🎟️ Você tem %d fichas.\n", jogador.getCarteira().getFichas());
        System.out.print("Quantas fichas deseja apostar?\n");
        System.out.print("\nFICHAS APOSTADAS: ");
        int valorApostado = lerInteiro(scanner.nextLine());

        if (valorApostado <= 0 || valorApostado < jogoSelecionado.getValorInicial()) {
            System.out.println("\n=======================================================");
            System.out.printf("❌ Aposta inválida. Mínimo: %d fichas.\n", jogoSelecionado.getValorInicial());
            System.out.println("=======================================================");
            return;
        }
        if (valorApostado > jogador.getCarteira().getFichas()) {
            System.out.println("❌ Você não tem fichas suficientes para essa aposta.");
            return;
        }

        int escolha = -1;
        // Da pra exibir as opções de aposta de acordo com o tipo de roleta. 
        // Um metodo na classe Jogo poderia printar as opçoes.
        if (opcaoEscolhida == 1) {
            System.out.println("\nEscolha sua aposta:");
            System.out.println("Digite '0' para escolher PAR");
            System.out.println("Digite '1' para escolher ÍMPAR");
            System.out.print("\nSUA ESCOLHA: ");
            escolha = lerInteiro(scanner.nextLine());
        } else if (opcaoEscolhida == 2) {
            System.out.println("\nEscolha sua cor:");
            System.out.println("Digite '0' para VERMELHO");
            System.out.println("Digite '1' para AZUL");
            System.out.println("Digite '2' para AMARELO");
            System.out.println("Digite '3' para VERDE");
            System.out.print("\nSUA ESCOLHA: ");
            escolha = lerInteiro(scanner.nextLine());
        }

        if (!jogoSelecionado.apostaValida(valorApostado, escolha)) {
            System.out.println("❌ Aposta cancelada.");
            return;
        }

        gerenciadorJogo.iniciarPartida(jogoSelecionado, jogador, valorApostado, escolha);
    }

    private static void menuCacaNiquel(Jogador jogador) {
        Scanner scanner = new Scanner(System.in);

        Jogo cacaNiquel = gerenciadorJogo.buscarJogo("Caça Níquel");

        System.out.println("\n🎰 Bem-vindo ao " + cacaNiquel.getNomeJogo() + "!");
        System.out.println("\n==========================================================================================");
        cacaNiquel.exibirRegras();
        System.out.println("==========================================================================================\n");
        System.out.printf("🎟️ Você tem %d fichas.\n", jogador.getCarteira().getFichas());
        System.out.print("Quantas fichas deseja apostar?\n");
        System.out.print("\nFICHAS APOSTADAS: ");
        int valorApostado = lerInteiro(scanner.nextLine());

        if (valorApostado <= 0 || valorApostado < cacaNiquel.getValorInicial()) {
            System.out.println("\n=======================================================");
            System.out.printf("❌ Aposta inválida. Mínimo: %d fichas.\n", cacaNiquel.getValorInicial());
            System.out.println("=======================================================");
            return;
        }
        if (valorApostado > jogador.getCarteira().getFichas()) {
            System.out.println("❌ Você não tem fichas suficientes para essa aposta.");
            return;
        }

        System.out.println("\nPressione enter para apertar a alavanca:");
        scanner.nextLine();

        if (!cacaNiquel.apostaValida(valorApostado, 0)) {
            System.out.println("❌ Aposta cancelada.");
            return;
        }

        gerenciadorJogo.iniciarPartida(cacaNiquel, jogador, valorApostado, 0);
    }

    private static int lerInteiro(String textoUsuario) {
        if (ehInteiro(textoUsuario)) {
            return Integer.parseInt(textoUsuario);
        }
        return -1;
    }

    private static Double lerDouble(String textoUsuario) {
        if (ehDouble(textoUsuario)) {
            return Double.parseDouble(textoUsuario);
        }
        return -1.0;
    }

    private static boolean ehInteiro(String texto) {
        return texto.matches("-?\\d+");
    }

    private static boolean ehDouble(String texto) {
        return texto.matches("-?\\d+(\\.\\d+)?");
    }
}
