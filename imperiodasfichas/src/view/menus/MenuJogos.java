package view.menus;

import controller.GerenciadorJogo;
import model.Jogador;
import model.jogos.Jogo;
import view.ImperioDasFichas;
import view.utils.LimparTerminal;
import view.utils.AnsiColors;

import java.util.Scanner;

public class MenuJogos {

    public static void executarMenu(Jogador jogador, GerenciadorJogo gerenciadorJogo) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n" + AnsiColors.PURPLE + "🎰 Escolha o tipo de Roleta:" + AnsiColors.RESET);
        System.out.println(AnsiColors.GREEN + "1. Roleta Clássica (⚪ Par/⚫ Ímpar)" + AnsiColors.RESET);
        System.out.println(AnsiColors.GREEN + "2. Roleta das Cores 🌈" + AnsiColors.RESET);
        System.out.print("\n" + AnsiColors.YELLOW + "🧭 Escolha uma opção: " + AnsiColors.RESET);
        int opcaoEscolhida = ImperioDasFichas.lerInteiro(scanner.nextLine());
        Jogo jogoSelecionado = null;

        switch (opcaoEscolhida) {
            case 1:
                try {
                    jogoSelecionado = gerenciadorJogo.buscarJogo("Roleta Clássica");
                } catch (Exception e) {
                    System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
                    return;
                }
                break;
            case 2:
                try {
                    jogoSelecionado = gerenciadorJogo.buscarJogo("Roleta das Cores");
                } catch (Exception e) {
                    System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
                    return;
                }
                break;
            default:
                System.out.println(AnsiColors.RED + "❌ Opção inválida." + AnsiColors.RESET);
                break;
        }

        if (jogoSelecionado == null) {
            System.out.println(AnsiColors.RED + "❌ Roleta não está disponível no momento." + AnsiColors.RESET);
            return;
        }

        System.out.println("\n" + AnsiColors.PURPLE + "🎰 Bem-vindo à " + jogoSelecionado.getNomeJogo() + "!" + AnsiColors.RESET);
        System.out.println("\n" + AnsiColors.YELLOW + "==========================================================================================" + AnsiColors.RESET);
        jogoSelecionado.exibirRegras();
        System.out.println(AnsiColors.YELLOW + "==========================================================================================" + AnsiColors.RESET + "\n");
        System.out.printf(AnsiColors.CYAN + "🎟️ Você tem %d fichas.\n" + AnsiColors.RESET, jogador.getCarteira().getFichas());
        System.out.print(AnsiColors.YELLOW + "Quantas fichas deseja apostar?\n" + AnsiColors.RESET);
        System.out.print("\n" + AnsiColors.CYAN + "FICHAS APOSTADAS: " + AnsiColors.RESET);
        int valorApostado = ImperioDasFichas.lerInteiro(scanner.nextLine());

        if (valorApostado <= 0 || valorApostado < jogoSelecionado.getValorInicial()) {
            System.out.println("\n" + AnsiColors.RED + "=======================================================" + AnsiColors.RESET);
            System.out.printf(AnsiColors.RED + "❌ Aposta inválida. Mínimo: %d fichas.\n" + AnsiColors.RESET, jogoSelecionado.getValorInicial());
            System.out.println(AnsiColors.RED + "=======================================================" + AnsiColors.RESET);
            return;
        }
        if (valorApostado > jogador.getCarteira().getFichas()) {
            System.out.println(AnsiColors.RED + "❌ Você não tem fichas suficientes para essa aposta." + AnsiColors.RESET);
            return;
        }

        int escolha = -1;
        if (opcaoEscolhida == 1) {
            System.out.println("\n" + AnsiColors.YELLOW + "Escolha sua aposta:" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "Digite '0' para escolher PAR" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "Digite '1' para escolher ÍMPAR" + AnsiColors.RESET);
            System.out.print("\n" + AnsiColors.CYAN + "SUA ESCOLHA: " + AnsiColors.RESET);
            escolha = ImperioDasFichas.lerInteiro(scanner.nextLine());
        } else if (opcaoEscolhida == 2) {
            System.out.println("\n" + AnsiColors.YELLOW + "Escolha sua cor:" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "Digite '0' para VERMELHO" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "Digite '1' para AZUL" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "Digite '2' para AMARELO" + AnsiColors.RESET);
            System.out.println(AnsiColors.GREEN + "Digite '3' para VERDE" + AnsiColors.RESET);
            System.out.print("\n" + AnsiColors.CYAN + "SUA ESCOLHA: " + AnsiColors.RESET);
            escolha = ImperioDasFichas.lerInteiro(scanner.nextLine());
        }

        try {
            jogoSelecionado.apostaValida(valorApostado, escolha);
        } catch (Exception e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
            System.out.println(AnsiColors.RED + "❌ Aposta cancelada." + AnsiColors.RESET);
            return;
        }

        try {
            gerenciadorJogo.iniciarPartida(jogoSelecionado, jogador, valorApostado, escolha);
        } catch (Exception e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
        }
    }

    public static void menuCacaNiquel(Jogador jogador, GerenciadorJogo gerenciadorJogo) {
        LimparTerminal.executar();
        Scanner scanner = new Scanner(System.in);

        Jogo cacaNiquel = null;
        try {
            cacaNiquel = gerenciadorJogo.buscarJogo("Caça Níquel");
        } catch (Exception e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
        }

        System.out.println("\n" + AnsiColors.PURPLE + "🎰 Bem-vindo ao " + cacaNiquel.getNomeJogo() + "!" + AnsiColors.RESET);
        System.out.println("\n" + AnsiColors.YELLOW + "==========================================================================================" + AnsiColors.RESET);
        cacaNiquel.exibirRegras();
        System.out.println(AnsiColors.YELLOW + "==========================================================================================" + AnsiColors.RESET + "\n");
        System.out.printf(AnsiColors.CYAN + "🎟️ Você tem %d fichas.\n" + AnsiColors.RESET, jogador.getCarteira().getFichas());
        System.out.print(AnsiColors.YELLOW + "Quantas fichas deseja apostar?\n" + AnsiColors.RESET);
        System.out.print("\n" + AnsiColors.CYAN + "FICHAS APOSTADAS: " + AnsiColors.RESET);
        int valorApostado = ImperioDasFichas.lerInteiro(scanner.nextLine());

        if (valorApostado <= 0 || valorApostado < cacaNiquel.getValorInicial()) {
            System.out.println("\n" + AnsiColors.RED + "=======================================================" + AnsiColors.RESET);
            System.out.printf(AnsiColors.RED + "❌ Aposta inválida. Mínimo: %d fichas.\n" + AnsiColors.RESET, cacaNiquel.getValorInicial());
            System.out.println(AnsiColors.RED + "=======================================================" + AnsiColors.RESET);
            return;
        }
        if (valorApostado > jogador.getCarteira().getFichas()) {
            System.out.println(AnsiColors.RED + "❌ Você não tem fichas suficientes para essa aposta." + AnsiColors.RESET);
            return;
        }

        System.out.println("\n" + AnsiColors.YELLOW + "Pressione enter para apertar a alavanca:" + AnsiColors.RESET);
        scanner.nextLine();

        try {
            cacaNiquel.apostaValida(valorApostado, 0);
        } catch (Exception e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
            System.out.println(AnsiColors.RED + "❌ Aposta cancelada." + AnsiColors.RESET);
            return;
        }

        try {
            gerenciadorJogo.iniciarPartida(cacaNiquel, jogador, valorApostado, 0);
        } catch (Exception e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
        }
    }

    public static void menuBlackJack(Jogador jogador, GerenciadorJogo gerenciadorJogo) {
        LimparTerminal.executar();
        Scanner scanner = new Scanner(System.in);

        Jogo blackJack = null;
        try {
            blackJack = gerenciadorJogo.buscarJogo("BlackJack");
        } catch (Exception e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
            return;
        }

        System.out.println("\n" + AnsiColors.PURPLE + "🃏 Bem-vindo ao " + blackJack.getNomeJogo() + "!" + AnsiColors.RESET);
        blackJack.exibirRegras();
        System.out.printf(AnsiColors.CYAN + "🎟️ Você tem %d fichas.\n" + AnsiColors.RESET, jogador.getCarteira().getFichas());
        System.out.print(AnsiColors.YELLOW + "Quantas fichas deseja apostar?\n" + AnsiColors.RESET);
        System.out.print("\n" + AnsiColors.CYAN + "FICHAS APOSTADAS: " + AnsiColors.RESET);
        int valorApostado = ImperioDasFichas.lerInteiro(scanner.nextLine());

        if (valorApostado <= 0 || valorApostado < blackJack.getValorInicial()) {
            System.out.printf(AnsiColors.RED + "❌ Aposta inválida. Mínimo: %d fichas.\n" + AnsiColors.RESET, blackJack.getValorInicial());
            return;
        }
        if (valorApostado > jogador.getCarteira().getFichas()) {
            System.out.println(AnsiColors.RED + "❌ Você não tem fichas suficientes para essa aposta." + AnsiColors.RESET);
            return;
        }

        try {
            gerenciadorJogo.iniciarPartida(blackJack, jogador, valorApostado, 0);
        } catch (Exception e) {
            System.out.println(AnsiColors.RED + e.getMessage() + AnsiColors.RESET);
        }
    }
}