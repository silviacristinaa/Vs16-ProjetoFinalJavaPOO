package model.jogos.blackjack;

import model.jogos.Jogo;
import model.Jogador;
import model.Partida;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackJack extends Jogo {

    public BlackJack(Integer idJogo, String nomeJogo, String regras) {
        super(idJogo, nomeJogo, regras, 10);
    }

    @Override
    public void exibirRegras() {
        System.out.println("\n========== Regras do BlackJack ==========");
        System.out.println("Chegue o mais próximo de 21 sem passar.");
        System.out.println("Cartas de 2 a 10 valem o valor do seu próprio número.");
        System.out.println("J/Q/K valem 10, Ás valem 1 ou 11.");
        System.out.println("O Dealer compra até ficar com pelo menos 17 pontos.");
        System.out.println("Se vencer, ganhe o triplo do valor apostado! 💰");
        System.out.println("=========================================\n");
    }

    @Override
    public Partida jogar(Jogador jogador, int valorApostado, int opcaoEscolhida) {
        Scanner scanner = new Scanner(System.in);

        apostaValida(valorApostado, opcaoEscolhida);

        jogador.getCarteira().removerFichas(valorApostado);

        List<BaralhoDeCartas> maoJogador = new ArrayList<>();
        List<BaralhoDeCartas> maoDealer = new ArrayList<>();

        maoJogador.add(BaralhoDeCartas.sorteiaCarta());
        maoJogador.add(BaralhoDeCartas.sorteiaCarta());

        maoDealer.add(BaralhoDeCartas.sorteiaCarta());
        maoDealer.add(BaralhoDeCartas.sorteiaCarta());

        System.out.println("\nSuas cartas:\n" + mostrarCartas(maoJogador) + " (Total: " + calcularPontuacao(maoJogador) + ")");
        System.out.println("\nCarta visível do dealer:\n" + mostrarCartasDealerInicial(maoDealer));

        // Eu jogo
        while (true) {
            System.out.println("\nO que vai fazer?\n>> [1] Comprar carta (Hit)\n>> [2] Parar (Stand)");
            System.out.print("\nSUA ESCOLHA: ");
            int escolha = Integer.parseInt(scanner.nextLine());

            if (escolha == 1) {
                BaralhoDeCartas novaCarta = BaralhoDeCartas.sorteiaCarta();
                maoJogador.add(novaCarta);
                int total = calcularPontuacao(maoJogador);
                System.out.println("\n=================================");
                System.out.println("Você comprou: [" + novaCarta.getNome() + "]");
                System.out.println("Sua mão:\n" + mostrarCartas(maoJogador) + " (Total: " + total + ")");
                System.out.println("=================================");

                if (total > 21) {
                    System.out.println("\nESTOUROU! VOCÊ PERDEU!!");
                    return new Partida(valorApostado, false, this, jogador);
                }
            } else if (escolha == 2) {
                break;
            }
            else {
                System.out.println("❌ Opção inválida! Escolha [1] para comprar uma carta ou [2] para parar.");
                continue;
            }
        }

        // Dealer joga
        while (calcularPontuacao(maoDealer) < 17) {
            maoDealer.add(BaralhoDeCartas.sorteiaCarta());
        }

        int totalJogador = calcularPontuacao(maoJogador);
        int totalDealer = calcularPontuacao(maoDealer);

        System.out.println("\n=================================");
        System.out.println("Cartas do Dealer:\n" + mostrarCartas(maoDealer) + " (Total: " + totalDealer + ")");
        System.out.println("=================================");

        if (totalDealer > 21) {
            System.out.println("\nDEALER ESTOUROU!!");
        }

        boolean empate = (totalJogador == totalDealer && totalJogador <= 21);
        boolean jogadorGanhou = (totalJogador > totalDealer && totalJogador <= 21) || (totalDealer > 21);

        if (empate) {
            jogador.getCarteira().adicionarFichas(valorApostado);
            System.out.println("\nEmpate! Sua aposta foi devolvida.");
        }
        else if (jogadorGanhou) {
            int premio = valorApostado * 3;
            jogador.getCarteira().adicionarFichas(premio);
            System.out.println("\nParabéns! Você ganhou " + premio + " fichas!");
        } else {
            System.out.println("\nDealer venceu. Boa sorte na próxima!");
        }

        return new Partida(valorApostado, jogadorGanhou, this, jogador);
    }

    private int calcularPontuacao(List<BaralhoDeCartas> mao) {
        int total = 0;
        int ases = 0;
        for (BaralhoDeCartas carta : mao) {
            total += carta.getValor();
            if (carta == BaralhoDeCartas.AS) {
                ases++;
            }
        }
        while (total > 21 && ases > 0) {
            total -= 10;
            ases--;
        }
        return total;
    }

    private String mostrarCartas(List<BaralhoDeCartas> mao) {
        String linha1 = "";
        String linha2 = "";
        String linha3 = "";
        String linha4 = "";
        String linha5 = "";

        for (BaralhoDeCartas carta : mao) {
            String[] desenho = carta.getDesenho();
            linha1 += desenho[0] + " ";
            linha2 += desenho[1] + " ";
            linha3 += desenho[2] + " ";
            linha4 += desenho[3] + " ";
            linha5 += desenho[4] + " ";
        }
        String cartasAlinhadas = linha1 + "\n" + linha2 + "\n" +  linha3 + "\n" +  linha4 + "\n" +  linha5;
        return cartasAlinhadas;
    }

    private String mostrarCartasDealerInicial(List<BaralhoDeCartas> maoDealer) {
        String[] primeiraCarta = maoDealer.get(0).getDesenho();
        String[] cartaVirada = {
                "+-----+",
                "|░░░░░|",
                "|░░░░░|",
                "|░░░░░|",
                "+-----+"
        };

        String linha1 = primeiraCarta[0] + " " + cartaVirada[0];
        String linha2 = primeiraCarta[1] + " " + cartaVirada[1];
        String linha3 = primeiraCarta[2] + " " + cartaVirada[2];
        String linha4 = primeiraCarta[3] + " " + cartaVirada[3];
        String linha5 = primeiraCarta[4] + " " + cartaVirada[4];

        String cartasAlinhadas = linha1 + "\n" + linha2 + "\n" +  linha3 + "\n" +  linha4 + "\n" +  linha5;
        return cartasAlinhadas;
    }

    @Override
    public void apostaValida(int valorApostado, int opcaoEscolhida) throws IllegalArgumentException {
        validarValor(valorApostado);
    }

    @Override
    public void validarOpcao(int opcaoEscolhida) {
        throw new UnsupportedOperationException("O BlackJack não possui opções de aposta válidas.");
    }

    @Override
    public boolean verificarResultado(int resultado, int opcaoEscolhida) {
        return false;
    }

    @Override
    public void exibirResultado(Partida partida, int resultado) {
        throw new UnsupportedOperationException("O método exibirResultado não é suportado no BlackJack.");
    }

}
