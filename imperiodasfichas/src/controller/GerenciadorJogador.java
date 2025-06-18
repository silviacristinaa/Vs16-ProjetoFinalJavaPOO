package controller;

import java.util.List;

import dao.JogadorDao;
import dao.PartidaDao;
import dao.interfaces.DaoGenerico;
import exceptions.DadosDuplicadosException;
import exceptions.NaoEncontradoException;
import exceptions.RegraDeNegocioException;
import model.Carteira;
import model.Jogador;
import model.Partida;

public class GerenciadorJogador {
    private final DaoGenerico<Jogador, String> daoGenerico;
    private final DaoGenerico<Partida, String> daoGenericoPartida;

    public GerenciadorJogador(JogadorDao jogadorDao, PartidaDao partidaDao) {
          this.daoGenerico = jogadorDao;
          this.daoGenericoPartida = partidaDao;
    }

    public Jogador buscarJogador(String nickname) throws NaoEncontradoException, RegraDeNegocioException {
        Jogador jogador = daoGenerico.buscar(nickname);
        if (jogador == null) {
            throw new NaoEncontradoException("❌ Verifique o nickname ou cadastre-se primeiro.");
        }
        return jogador;
    }

    public Jogador adicionarJogador(String nome, int idade, String nickname) throws DadosDuplicadosException, RegraDeNegocioException {
        if (jogadorExiste(nickname)) {
            throw new DadosDuplicadosException("Jogador com o nickname " + nickname + " já existe.");
        }
        Jogador novo = new Jogador(nome, idade, nickname);
        return daoGenerico.adicionar(novo);
    }

    public Jogador adicionarJogador(String nome, int idade, String nickname, int quantidadeFichas) throws DadosDuplicadosException, RegraDeNegocioException {
        if (jogadorExiste(nickname)) {
            throw new DadosDuplicadosException("⚠️ Jogador com o nickname " + nickname + " já existe. Escolha outro para continuar.");
        }
        Carteira carteira = new Carteira(quantidadeFichas, 0);
        Jogador novo = new Jogador(nome, idade, nickname, carteira);
        return daoGenerico.adicionar(novo);
    }

    public boolean jogadorExiste(String nicknameJogador) throws RegraDeNegocioException {
        return daoGenerico.buscar(nicknameJogador) != null;
    }    

    public boolean removerJogador(String nicknameJogador) throws NaoEncontradoException, RegraDeNegocioException {
        Jogador jogador = buscarJogador(nicknameJogador);

        Partida partida = daoGenericoPartida.buscar(jogador.getIdJogador().toString());
        partida.setJogador(jogador);

        daoGenericoPartida.remover(partida);
        return daoGenerico.remover(jogador);
    }

    public Jogador atualizarJogador(Jogador jogador, String nickname) throws RegraDeNegocioException {
        return daoGenerico.atualizar(jogador, nickname);
    }

    public void fazerDeposito(String nicknameJogador, double valor) throws NaoEncontradoException, IllegalArgumentException, RegraDeNegocioException {
        Jogador jogador = buscarJogador(nicknameJogador);
        if (!jogador.getCarteira().depositarDinheiro(valor)) {
            throw new IllegalArgumentException("❌ Valor de depósito inválido: " + valor);
        }
    }
    public void fazerSaque(String nicknameJogador, double valor) throws NaoEncontradoException, IllegalArgumentException, RegraDeNegocioException {
        if (valor <= 0) {
            throw new IllegalArgumentException("⚠️ Valor de saque deve ser positivo: " + valor);
        }
        Jogador jogador = buscarJogador(nicknameJogador);
        if (!jogador.getCarteira().sacarDinheiro(valor)) {
            throw new IllegalArgumentException("⚠️ Saldo insuficiente para saque: " + valor);
        }
    }

    public void exibirPartidasJogadas(String nicknameJogador) throws NaoEncontradoException, RegraDeNegocioException {
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

    public List<Jogador> listarJogadoresPorVitorias() throws RegraDeNegocioException {
        List<Jogador> jogadores = daoGenerico.listar();

        return jogadores.stream().filter(j -> !j.getPartidas().isEmpty())
                .sorted((j1, j2) -> Integer.compare(
                    j2.getPartidas().stream().mapToInt(p -> p.isGanhou() ? 1 : 0).sum(),
                    j1.getPartidas().stream().mapToInt(p -> p.isGanhou() ? 1 : 0).sum()))
                .toList();
    }
}
