package controller;

import dao.interfaces.DaoGenerico;
import exceptions.DadosDuplicadosException;
import exceptions.NaoEncontradoException;
import model.Carteira;
import model.Jogador;

public class GerenciadorJogador {
    private final DaoGenerico<Jogador, String> daoGenerico;

    public GerenciadorJogador(DaoGenerico<Jogador, String> daoGenerico) {
        this.daoGenerico = daoGenerico;
    }

    public Jogador buscarJogador(String nickname) throws NaoEncontradoException {
        Jogador jogador = daoGenerico.buscar(nickname);
        if (jogador == null) {
            throw new NaoEncontradoException("Jogador com o nickname " + nickname + " não encontrado.");
        }
        return jogador;
    }

    public Jogador adicionarJogador(String nome, int idade, String nickname) throws DadosDuplicadosException {
        if (jogadorExiste(nickname)) {
            throw new DadosDuplicadosException("Jogador com o nickname " + nickname + " já existe.");
        }
        Jogador novo = new Jogador(nome, idade, nickname);
        return daoGenerico.adicionar(novo);
    }
    public Jogador adicionarJogador(String nome, int idade, String nickname, int quantidadeFichas) throws DadosDuplicadosException {
        if (jogadorExiste(nickname)) {
            throw new DadosDuplicadosException("Jogador com o nickname " + nickname + " já existe.");
        }
        Carteira carteira = new Carteira(quantidadeFichas, 0);
        Jogador novo = new Jogador(nome, idade, nickname, carteira);
        return daoGenerico.adicionar(novo);
    }

    public boolean jogadorExiste(String nicknameJogador) {
        return daoGenerico.buscar(nicknameJogador) != null;
    }    

    public boolean removerJogador(String nicknameJogador) throws NaoEncontradoException {
        Jogador jogador = buscarJogador(nicknameJogador);
        return daoGenerico.remover(jogador);
    }

    public Jogador atualizarJogador(Jogador jogador, String nickname) {
        return daoGenerico.atualizar(jogador, nickname);
    }

    public void fazerDeposito(String nicknameJogador, double valor) throws NaoEncontradoException, IllegalArgumentException {
        Jogador jogador = buscarJogador(nicknameJogador);
        if (!jogador.getCarteira().depositarDinheiro(valor)) {
            throw new IllegalArgumentException("Valor de depósito inválido: " + valor);
        }
    }
    public void fazerSaque(String nicknameJogador, double valor) throws NaoEncontradoException, IllegalArgumentException {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo: " + valor);
        }
        Jogador jogador = buscarJogador(nicknameJogador);
        if (!jogador.getCarteira().sacarDinheiro(valor)) {
            throw new IllegalArgumentException("Saldo insuficiente para saque: " + valor);
        }
    }

    public void exibirPartidasJogadas(String nicknameJogador) throws NaoEncontradoException {
        Jogador jogador = buscarJogador(nicknameJogador);
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
