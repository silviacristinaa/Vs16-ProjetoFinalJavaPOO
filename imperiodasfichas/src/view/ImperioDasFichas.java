package view;

import controller.GerenciadorJogador;
import controller.GerenciadorJogo;
import dao.JogadorDao;
import dao.JogoDao;
import exceptions.RegraDeNegocioException;
import model.jogos.Jogo;
import model.jogos.blackjack.BlackJack;
import model.jogos.cacaniquel.CacaNiquel;
import model.jogos.roletas.RoletaCores;
import model.jogos.roletas.RoletaParImpar;
import view.animacoes.AnimacaoEntrada;
import view.menus.MenuInicial;

public class ImperioDasFichas {

    static final GerenciadorJogador gerenciadorJogador = new GerenciadorJogador(new JogadorDao());
    static final GerenciadorJogo gerenciadorJogo = new GerenciadorJogo("Império das Fichas", 1.00, gerenciadorJogador, new JogoDao());

    public static void main(String[] args) throws InterruptedException, RegraDeNegocioException {

        AnimacaoEntrada.executar();

        // Adição dos jogos de roleta
        Jogo roleta = new RoletaParImpar("Roleta Clássica", "Aposte em ⚪ PAR (0) ou ⚫ ÍMPAR (1). Se acertar, ganha o dobro do valor apostado! 💰");
        Jogo roletaCores = new RoletaCores("Roleta das Cores", "Aposte em uma cor: VERMELHO (0), AZUL (1), AMARELO (2), VERDE (3). 🍀 Acerte e ganhe 4x! 💰");
        Jogo cacaNiquel = new CacaNiquel("Caça Níquel", "🎰 Aperte a alavanca da sorte. Se acertar, ganhe o dobro do valor apostado! 💰");
        Jogo blackJack = new BlackJack("BlackJack", "🃏 Chegue o mais próximo de 21 sem ultrapassar. Se vencer, ganhe o triplo do valor apostado! 💰");

        try {
            gerenciadorJogo.adicionarJogo(roleta);
            gerenciadorJogo.adicionarJogo(roletaCores);
            gerenciadorJogo.adicionarJogo(cacaNiquel);
            gerenciadorJogo.adicionarJogo(blackJack);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        MenuInicial.executarMenu(gerenciadorJogador, gerenciadorJogo);
    }

    public static int lerInteiro(String textoUsuario) {
        try {
            return Integer.parseInt(textoUsuario);
        } catch (Exception e) {
            return -1;
        }
    }

    public static Double lerDouble(String textoUsuario) {
        try {
            return Double.parseDouble(textoUsuario);
        } catch (Exception e) {
           return -1.0;
        }
    }
}
