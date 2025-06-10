package controller;

import dao.interfaces.DaoGenerico;
import exceptions.DadosDuplicados;
import exceptions.NaoEncontradoException;
import exceptions.ValorInvalido;
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

    public Partida iniciarPartida(Jogo jogo, Jogador jogador, int quantidadeFichaAposta, int opcaoEscolhida) throws Exception {
        if (quantidadeFichaAposta <= 0) {
            throw new ValorInvalido("Quantidade de fichas apostadas deve ser positiva: " + quantidadeFichaAposta);
        }
        if (jogador.getCarteira().getFichas() < quantidadeFichaAposta) {
            throw new ValorInvalido("Jogador não possui fichas suficientes para apostar: " + quantidadeFichaAposta);
        }
        Partida partida = jogo.jogar(jogador,quantidadeFichaAposta, opcaoEscolhida);
        jogador.getPartidas().add(partida);
        return partida;
    }

    public Jogo adicionarJogo(Jogo jogo) throws DadosDuplicados {
        if (jogoExiste(jogo.getNomeJogo())) {
            throw new DadosDuplicados("Jogo com o nome " + jogo.getNomeJogo() + " já existe.");
        }
        return daoGenerico.adicionar(jogo);
    }
    public boolean removerJogo(String nomeJogo) throws NaoEncontradoException {
        Jogo jogo = buscarJogo(nomeJogo);
        return daoGenerico.remover(jogo);
    }

    public boolean jogoExiste(String nomeJogo) {
        return daoGenerico.buscar(nomeJogo) != null;
    }

    public Jogo buscarJogo(String nomeJogo) throws NaoEncontradoException {
        Jogo jogo = daoGenerico.buscar(nomeJogo);
        if (jogo == null) {
            throw new NaoEncontradoException("Jogo com o nome " + nomeJogo + " não encontrado.");
        }
        return jogo;
    }

    public void comprarFicha(String nicknameJogador, int quantidadeFicha) throws Exception{
        if (quantidadeFicha <= 0) throw new ValorInvalido("Quantidade de fichas deve ser positiva: " + quantidadeFicha);
        Jogador jogador = gerenciadorJogador.buscarJogador(nicknameJogador);
        if(jogador.getCarteira().getDinheiro() < quantidadeFicha * valorFicha) throw new ValorInvalido("Jogador não possui dinheiro suficiente para comprar fichas.");
        jogador.getCarteira().depositarFichasCompradas(quantidadeFicha, getValorFicha());
    }
    public void venderFicha(String nicknameJogador, int quantidadeFicha) throws Exception {
        if (quantidadeFicha <= 0) throw new ValorInvalido("Quantidade de fichas deve ser positiva: " + quantidadeFicha);
        Jogador jogador = gerenciadorJogador.buscarJogador(nicknameJogador);
        if(!jogador.getCarteira().sacarFichasVendidas(quantidadeFicha, getValorFicha())) {
            throw new ValorInvalido("Jogador não possui "+ quantidadeFicha+ " fichas para vender.");
        }
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