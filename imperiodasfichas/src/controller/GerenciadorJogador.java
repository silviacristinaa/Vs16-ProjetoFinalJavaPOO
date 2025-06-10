package controller;

import dao.interfaces.DaoGenerico;
import model.Carteira;
import model.Jogador;

public class GerenciadorJogador {
    private final DaoGenerico<Jogador, String> daoGenerico;

    public GerenciadorJogador(DaoGenerico<Jogador, String> daoGenerico) {
        this.daoGenerico = daoGenerico;
    }

    public Jogador buscarJogador(String nickname) {
        Jogador jogador = daoGenerico.buscar(nickname);
        if (jogador == null) {
            System.out.println("Jogador não encontrado.");
            return null;
        }
        return jogador;
    }

    public Jogador adicionarJogador(String nome, int idade, String nickname) {
        Jogador novo = new Jogador(nome, idade, nickname);
        if (jogadorExiste(nickname)) {
            System.out.println("Jogador com o nickname " + nickname + " já existe.");
            return null;
        }
        return daoGenerico.adicionar(novo);
    }
    public Jogador adicionarJogador(String nome, int idade, String nickname, int quantidadeFichas) {
        Carteira carteira = new Carteira(quantidadeFichas, 0);
        Jogador novo = new Jogador(nome, idade, nickname, carteira);
        return daoGenerico.adicionar(novo);
    }

    public boolean jogadorExiste(String nicknameJogador) {
        return daoGenerico.buscar(nicknameJogador) != null;
    }    

    public boolean removerJogador(String nicknameJogador) {
        Jogador jogador = buscarJogador(nicknameJogador);
        if (jogador != null) {
            return daoGenerico.remover(jogador);
        }
        return false;
    }

    public Jogador atualizarJogador(Jogador jogador, String nickname) {
        return daoGenerico.atualizar(jogador, nickname);
    }

    public boolean fazerDeposito(String nicknameJogador, double valor) {
        Jogador jogador = buscarJogador(nicknameJogador);
        if (jogador != null) {
            return jogador.getCarteira().depositarDinheiro(valor);
        }
        return false;
    }
    public boolean fazerSaque(String nicknameJogador, double valor) {
        Jogador jogador = buscarJogador(nicknameJogador);
        if (jogador != null) {
            return jogador.getCarteira().sacarDinheiro(valor);
        }
        return false;
    }

    public void exibirPartidasJogadas(String nicknameJogador) {
        Jogador jogador = buscarJogador(nicknameJogador);
        if (jogador != null) {
            if (jogador.getPartidas().isEmpty()) {
                System.out.println("Nenhuma partida jogada.");
            } else {
                System.out.println("Partidas jogadas por " + jogador.getNickname() + ":");
                for (var partida : jogador.getPartidas()) {
                    System.out.println(partida);
                }
            }
        }
    }

}
