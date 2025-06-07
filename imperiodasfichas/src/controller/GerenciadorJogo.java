package controller;

import dao.JogoDao;
import model.Jogador;
import model.jogos.Jogo;

public class GerenciadorJogo {
    private String nome;
    private double valorFicha;
    private final JogoDao jogoDao;
    private final GerenciadorJogador gerenciadorJogador;

    public GerenciadorJogo(String nome, double valorFicha, GerenciadorJogador gerenciadorJogador) {
        this.gerenciadorJogador = gerenciadorJogador;
        this.nome = nome;
        this.valorFicha = valorFicha;
        this.jogoDao = new JogoDao();
    }

    public Jogo adicionarJogo(Jogo jogo) {
        return jogoDao.adicionar(jogo);
    }
    public boolean removerJogo(String nomeJogo) {
        Jogo jogo = buscarJogo(nomeJogo);
        if (jogo == null) {
            System.out.println("Jogo não encontrado.");
            return false;
        }
        return jogoDao.remover(jogo);
    }

    public Jogo buscarJogo(String nomeJogo) {
        return jogoDao.buscar(nomeJogo);
    }

    public boolean comprarFicha(String nicknameJogador, int quantidadeFicha) {
        Jogador jogador = gerenciadorJogador.buscarJogador(nicknameJogador);
        if (jogador != null) {
            if(jogador.getCarteira().getDinheiro() < quantidadeFicha * valorFicha) {
                System.out.println("Jogador não possui dinheiro suficiente para comprar as fichas.");
                return false;
            }
            return jogador.getCarteira().depositarFichas(quantidadeFicha);
        }
        return false;
    }
    public boolean venderFicha(String nicknameJogador, int quantidadeFicha) {
        Jogador jogador = gerenciadorJogador.buscarJogador(nicknameJogador);
        if (jogador != null) {
            if(!jogador.getCarteira().sacarFichas(quantidadeFicha)) {
                System.out.println("Jogador só possui " + jogador.getCarteira().getFichas() + " fichas.");
                return false;
            }
            return jogador.getCarteira().depositarDinheiro(quantidadeFicha * valorFicha);
        }
        return false;
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


}