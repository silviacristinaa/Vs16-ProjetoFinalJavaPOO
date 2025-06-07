package controller;

import java.util.ArrayList;
import java.util.List;

import model.Jogador;
import model.jogos.Jogo;

public class GerenciadorJogo {
    private String nome;
    private double valorFicha;
    private List<Jogador> jogadores;
    private List<Jogo> jogosDisponiveis;

    public GerenciadorJogo(String nome, double valorFicha, List<Jogo> jogosDisponiveis) {
        this.nome = nome;
        this.valorFicha = valorFicha;
        this.jogadores = new ArrayList<>();
        this.jogosDisponiveis = jogosDisponiveis;
    }

    public boolean adicionarJogo(Jogo jogo) {
        if (jogosDisponiveis.contains(jogo)) {
            return false;
        }
        return jogosDisponiveis.add(jogo);
    }
    public boolean removerJogo(String nomeJogo) {
        Jogo jogo = buscarJogo(nomeJogo);
        if (jogo == null) {
            return false;
        }
        return jogosDisponiveis.remove(jogo);
    }

    public Jogo buscarJogo(String nomeJogo) {
        for (Jogo jogo : jogosDisponiveis) {
            if (jogo.getNomeJogo().equals(nomeJogo)) {
                return jogo;
            }
        }
        return null;
    }

    public Jogador buscarJogador(String nickname) {
        for (Jogador jogador : jogadores) {
            if (jogador.getNickname().equals(nickname)) {
                return jogador;
            }
        }
        return null;
    }

    public boolean adicionarJogador(String nome, int idade, String nickname) {
        Jogador novo = new Jogador(nome, idade, nickname);
        return adicionarJogador(novo);
    }

    public boolean adicionarJogador(Jogador jogador) {
        if (jogadores.contains(jogador)) {
            return false;
        }
        return jogadores.add(jogador);
    }

    public boolean removerJogador(String nicknameJogador) {
        Jogador jogador = buscarJogador(nicknameJogador);
        if (jogador == null) {
            System.out.println("Jogador não encontrado.");
            return false;
        }
        return jogadores.remove(jogador);
    }

    public boolean comprarFicha(String nicknameJogador, int quantidadeFicha) {
        Jogador jogador = buscarJogador(nicknameJogador);
        if (jogador == null) {
            System.out.println("Jogador não encontrado.");
            return false;
        }
        if(jogador.getCarteira().getDinheiro() < quantidadeFicha * valorFicha) {
            System.out.println("Jogador não possui dinheiro suficiente para comprar as fichas.");
            return false;
        }
        return jogador.getCarteira().depositarFichas(quantidadeFicha);
    }

    public boolean venderFicha(String nicknameJogador, int quantidadeFicha) {
        Jogador jogador = buscarJogador(nicknameJogador);
        if (jogador == null) {
            System.out.println("Jogador não encontrado");
            return false;
        }
        if(!jogador.getCarteira().sacarFichas(quantidadeFicha)) {
            System.out.println("Jogador só possui " + jogador.getCarteira().getFichas() + " fichas.");
            return false;
        }
        return jogador.getCarteira().depositarDinheiro(quantidadeFicha * valorFicha);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorFicha() {
        return valorFicha;
    }

    public void setValorFicha(double valorFicha) {
        this.valorFicha = valorFicha;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public List<Jogo> getJogosDisponiveis() {
        return jogosDisponiveis;
    }

    public void setJogosDisponiveis(List<Jogo> jogosDisponiveis) {
        this.jogosDisponiveis = jogosDisponiveis;
    }


}