package view.menus;

import controller.GerenciadorJogo;
import model.Jogador;
import model.jogos.Jogo;
import view.ImperioDasFichas;

import java.util.Scanner;

public class MenuJogos {

    public static void executarMenu(Jogador jogador, GerenciadorJogo gerenciadorJogo) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🎰 Escolha o tipo de Roleta:");
        System.out.println("1. Roleta Clássica (⚪ Par/⚫ Ímpar)");
        System.out.println("2. Roleta das Cores 🌈");
        System.out.print("\n🧭 Escolha uma opção: ");
        int opcaoEscolhida = ImperioDasFichas.lerInteiro(scanner.nextLine());
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
        int valorApostado = ImperioDasFichas.lerInteiro(scanner.nextLine());

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
            escolha = ImperioDasFichas.lerInteiro(scanner.nextLine());
        } else if (opcaoEscolhida == 2) {
            System.out.println("\nEscolha sua cor:");
            System.out.println("Digite '0' para VERMELHO");
            System.out.println("Digite '1' para AZUL");
            System.out.println("Digite '2' para AMARELO");
            System.out.println("Digite '3' para VERDE");
            System.out.print("\nSUA ESCOLHA: ");
            escolha = ImperioDasFichas.lerInteiro(scanner.nextLine());
        }

        if (!jogoSelecionado.apostaValida(valorApostado, escolha)) {
            System.out.println("❌ Aposta cancelada.");
            return;
        }

        gerenciadorJogo.iniciarPartida(jogoSelecionado, jogador, valorApostado, escolha);
    }

    public static void menuCacaNiquel(Jogador jogador, GerenciadorJogo gerenciadorJogo) {
        Scanner scanner = new Scanner(System.in);

        Jogo cacaNiquel = gerenciadorJogo.buscarJogo("Caça Níquel");

        System.out.println("\n🎰 Bem-vindo ao " + cacaNiquel.getNomeJogo() + "!");
        System.out.println("\n==========================================================================================");
        cacaNiquel.exibirRegras();
        System.out.println("==========================================================================================\n");
        System.out.printf("🎟️ Você tem %d fichas.\n", jogador.getCarteira().getFichas());
        System.out.print("Quantas fichas deseja apostar?\n");
        System.out.print("\nFICHAS APOSTADAS: ");
        int valorApostado = ImperioDasFichas.lerInteiro(scanner.nextLine());

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

    public static void menuBlackJack(Jogador jogador, GerenciadorJogo gerenciadorJogo) {
        Scanner scanner = new Scanner(System.in);

        Jogo blackJack = gerenciadorJogo.buscarJogo("BlackJack");

        if (blackJack == null) {
            System.out.println("❌ Jogo BlackJack não está disponível no momento.");
            return;
        }

        System.out.println("\n🃏 Bem-vindo ao " + blackJack.getNomeJogo() + "!");
        blackJack.exibirRegras();
        System.out.printf("🎟️ Você tem %d fichas.\n", jogador.getCarteira().getFichas());
        System.out.print("Quantas fichas deseja apostar?\n");
        System.out.print("\nFICHAS APOSTADAS: ");
        int valorApostado = ImperioDasFichas.lerInteiro(scanner.nextLine());

        if (valorApostado <= 0 || valorApostado < blackJack.getValorInicial()) {
            System.out.printf("❌ Aposta inválida. Mínimo: %d fichas.\n", blackJack.getValorInicial());
            return;
        }
        if (valorApostado > jogador.getCarteira().getFichas()) {
            System.out.println("❌ Você não tem fichas suficientes para essa aposta.");
            return;
        }

        gerenciadorJogo.iniciarPartida(blackJack, jogador, valorApostado, 0);
    }
}
