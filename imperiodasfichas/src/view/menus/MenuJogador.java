package view.menus;

import controller.GerenciadorJogador;
import controller.GerenciadorJogo;
import model.Jogador;
import view.ImperioDasFichas;

import java.util.Scanner;

public class MenuJogador {

    public static void controleLogin(GerenciadorJogador gerenciadorJogador, GerenciadorJogo gerenciadorJogo) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🔐 Império das Fichas - Sistema de Login");

        System.out.print("🎲 Digite o seu nickname: ");
        String nickname = scanner.nextLine();

        Jogador jogador = gerenciadorJogador.buscarJogador(nickname);

        if (jogador == null) {
            System.out.println("❌ Verifique o nickname ou cadastre-se primeiro.");
            return;
        }

        System.out.printf("✅ Login realizado com sucesso! Bem-vindo, %s!!\n", jogador.getNickname());
        MenuOpcoes.executarMenu(jogador, gerenciadorJogador, gerenciadorJogo);
    }

    public static void menuEditarJogador(Jogador jogador, GerenciadorJogador gerenciadorJogador) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {

            System.out.println("\n✏️ Menu de Edição do Jogador");
            System.out.println(jogador + "\n");

            System.out.println("1. Editar Nome");
            System.out.println("2. Editar Idade");
            System.out.println("3. Voltar");

            System.out.print("\n🧭 Escolha uma opção: ");
            opcao = ImperioDasFichas.lerInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Digite o novo nome: ");
                    String novoNome = scanner.nextLine();
                    jogador.setNome(novoNome);
                    gerenciadorJogador.atualizarJogador(jogador, jogador.getNickname());
                    System.out.println("✅ Nome atualizado com sucesso!");
                    break;

                case 2:
                    System.out.print("Digite a nova idade: ");
                    int novaIdade = ImperioDasFichas.lerInteiro(scanner.nextLine());
                    if (novaIdade < 18) {
                        System.out.println("❌ Idade inválida! Apenas maiores de 18 anos.");
                        break;
                    }
                    jogador.setIdade(novaIdade);
                    gerenciadorJogador.atualizarJogador(jogador, jogador.getNickname());
                    System.out.println("✅ Idade atualizada com sucesso!");
                    break;

                case 3:
                    System.out.println("🔙 Retornando...");
                    break;

                default:
                    System.out.println("⚠️ Opção inválida!");
                    break;
            }

        } while (opcao != 3);
    }

    public static boolean menuDeletarJogador(Jogador jogador, GerenciadorJogador gerenciadorJogador) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n🗑️️ Menu de Deletar Jogador(a)");

            System.out.println("1. Remover Conta");
            System.out.println("2. Voltar");

            System.out.print("\n🧭 Escolha uma opção: ");
            opcao = ImperioDasFichas.lerInteiro(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Digite o seu nickname para confirmar a exclusão da conta: ");
                    String nickname = scanner.nextLine();

                    if (!jogador.getNickname().equals(nickname)) {
                        System.out.println("❌ Verifique o nickname digitado e tente novamente.");
                        break;
                    }

                    gerenciadorJogador.removerJogador(nickname);

                    System.out.println("✅ Sua conta foi removida com sucesso!");
                    return true;
                case 2:
                    System.out.println("🔙 Retornando...");
                    break;

                default:
                    System.out.println("⚠️ Opção inválida!");
                    break;
            }

        } while (opcao != 2);

        return false;
    }
}
