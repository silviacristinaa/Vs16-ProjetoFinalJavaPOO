package controller;

import dao.interfaces.DaoGenerico;
import model.Jogador;
import model.Partida;
import model.jogos.Jogo;

public class GerenciadorJogo {
    private String nome;
    private double valorFicha;
    private final DaoGenerico<Jogo, String> daoGenerico;
    private final GerenciadorJogador gerenciadorJogador;

    public GerenciadorJogo(String nome, double valorFicha, GerenciadorJogador gerenciadorJogador, DaoGenerico<Jogo, String> daoGenerico) {
        this.daoGenerico = daoGenerico;
        this.gerenciadorJogador = gerenciadorJogador;
        this.nome = nome;
        this.valorFicha = valorFicha;
    }

    public Partida iniciarPartida(Jogo jogo, Jogador jogador, int quantidadeFichaAposta, int opcaoEscolhida) {
        Partida partida = jogo.jogar(jogador,quantidadeFichaAposta, opcaoEscolhida);
        jogador.getPartidas().add(partida);
        return partida;
    }

    public Jogo adicionarJogo(Jogo jogo) {
        if (buscarJogo(jogo.getNomeJogo()) != null) {
            System.out.println("Jogo com o nome " + jogo.getNomeJogo() + " já existe.");
            return null;
        }
        return daoGenerico.adicionar(jogo);
    }
    public boolean removerJogo(String nomeJogo) {
        Jogo jogo = buscarJogo(nomeJogo);
        if (jogo == null) {
            System.out.println("Jogo não encontrado.");
            return false;
        }
        return daoGenerico.remover(jogo);
    }

    public Jogo buscarJogo(String nomeJogo) {
        return daoGenerico.buscar(nomeJogo);
    }

    public boolean comprarFicha(String nicknameJogador, int quantidadeFicha) {
        Jogador jogador = gerenciadorJogador.buscarJogador(nicknameJogador);
        if (jogador != null) {
            if(jogador.getCarteira().getDinheiro() < quantidadeFicha * valorFicha) {
                System.out.println("❌ Dinheiro insuficiente para comprar as fichas!");
                return false;
            }
            return jogador.getCarteira().depositarFichasCompradas(quantidadeFicha, getValorFicha());
        }
        return false;
    }
    public boolean venderFicha(String nicknameJogador, int quantidadeFicha) {
        Jogador jogador = gerenciadorJogador.buscarJogador(nicknameJogador);
        if (jogador != null) {
            if(!jogador.getCarteira().sacarFichasVendidas(quantidadeFicha, getValorFicha())) {
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