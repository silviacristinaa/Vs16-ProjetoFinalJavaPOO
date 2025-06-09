package controller;

import dao.JogadorDao;
import model.Carteira;
import model.Jogador;

public class GerenciadorJogador {
    private final JogadorDao jogadorDao;

    public GerenciadorJogador() {
        this.jogadorDao = new JogadorDao();
    }

    public Jogador buscarJogador(String nickname) {
        Jogador jogador = jogadorDao.buscar(nickname);
        if (jogador == null) {
            System.out.println("Jogador não encontrado.");
            return null;
        }
        return jogador;
    }

    public Jogador adicionarJogador(String nome, int idade, String nickname) {
        Jogador novo = new Jogador(nome, idade, nickname);
        return jogadorDao.adicionar(novo);
    }
    public Jogador adicionarJogador(String nome, int idade, String nickname, int quantidadeFichas) {
        Carteira carteira = new Carteira(quantidadeFichas, 0);
        Jogador novo = new Jogador(nome, idade, nickname, carteira);
        return jogadorDao.adicionar(novo);
    }

    public boolean removerJogador(String nicknameJogador) {
        Jogador jogador = buscarJogador(nicknameJogador);
        if (jogador != null) {
            return jogadorDao.remover(jogador);
        }
        return false;
    }

    public Jogador atualizarJogador(Jogador jogador, String nickname) {
        return jogadorDao.atualizar(jogador, nickname);
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

}
