package controller;

import java.util.ArrayList;
import java.util.List;

import model.Jogador;
import model.Jogo;

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
    public boolean removerJogo(Jogo jogo) {
        return jogosDisponiveis.remove(jogo);
    }

    public boolean adicionarJogador(Jogador jogador) {
        if (jogador.getIdade() < 18 || jogadores.contains(jogador)) {
            return false;
        }
        return jogadores.add(jogador);
    }

    public boolean removerJogador(Jogador jogador) {
        return jogadores.remove(jogador);
    }

    public boolean comprarFicha(Jogador jogador,int quantidadeFicha) {
        if(jogador.getCarteira().getDinheiro() < quantidadeFicha * valorFicha || !jogadores.contains(jogador)) {
            return false;
        }
        return jogador.getCarteira().depositarFichas(quantidadeFicha);
    }

    public boolean venderFicha(Jogador jogador, int quantidadeFicha) {
        if(!jogadores.contains(jogador) || !jogador.getCarteira().sacarFichas(quantidadeFicha)) {
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