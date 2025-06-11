package view.menus;

import controller.GerenciadorJogador;
import controller.GerenciadorJogo;
import model.Jogador;
import view.ImperioDasFichas;

import java.util.Scanner;

public class MenuInicial {

    public static void executarMenu(GerenciadorJogador gerenciadorJogador, GerenciadorJogo gerenciadorJogo) {
        Scanner scanner = new Scanner(System.in);

        int opcao;
        Jogador jogador = null;

        do {
            System.out.println("\n=======================================");
            System.out.println("🎰  BEM-VINDO AO IMPÉRIO DAS FICHAS!  🎰");
            System.out.println("=======================================\n");

            System.out.println("‍🎮  Menu Inicial\n");

            System.out.println("🆕  1. Cadastrar novo jogador");
            System.out.println("🔐  2. Realizar login");
            System.out.println("🚪  3. Desistir de tentar a sorte");

            System.out.print("\n🧭 Escolha uma opção: ");

            opcao = ImperioDasFichas.lerInteiro(scanner.nextLine());

            switch (opcao) {

                case 1:
                    System.out.println("✅ Cadastro de jogador iniciado com sucesso!\n");

                    System.out.print("👤 Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("🗓️ Idade: ");
                    int idade = ImperioDasFichas.lerInteiro(scanner.nextLine());

                    if (idade < 18) {
                        System.out.println("⚠️ Entrada proibida: apenas maiores de 18 anos podem jogar no Império das Fichas.");
                        break;
                    }

                    System.out.print("🎲 Nickname: ");
                    String nickname = scanner.nextLine();

                    try {
                        jogador = gerenciadorJogador.adicionarJogador(nome, idade, nickname);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    MenuOpcoes.executarMenu(jogador, gerenciadorJogador, gerenciadorJogo);

                    break;
                case 2:
                    MenuJogador.controleLogin(gerenciadorJogador, gerenciadorJogo);
                    break;
                case 3:
                    System.out.println("\n👋 Obrigado(a) por jogar! Volte sempre!");
                    break;

                default:
                    System.out.println("\n⚠️ Opção inválida! Por favor, tente novamente.");
                    break;
            }

        } while (opcao != 3);

    }
}
