package controller;

import dao.PartidaDao;
import dao.interfaces.DaoGenerico;
import exceptions.NaoEncontradoException;
import exceptions.RegraDeNegocioException;
import model.Jogador;
import model.Partida;
import model.jogos.Jogo;
import dao.db.DataBaseSingleton;
import java.sql.Connection;
import java.sql.SQLException; 


public class GerenciadorJogo {
    private String nome;
    private double valorFicha;
    private final DaoGenerico<Jogo, String> daoJogoGenerico;
    private final DaoGenerico<Partida, String> daoPartidaGenerico;
    private final GerenciadorJogador gerenciadorJogador;

    public GerenciadorJogo(String nome, double valorFicha, GerenciadorJogador gerenciadorJogador, DaoGenerico<Jogo, String> daoGenerico,
                           PartidaDao partidaDao) {
        this.daoJogoGenerico = daoGenerico;
        this.daoPartidaGenerico = partidaDao;
        this.gerenciadorJogador = gerenciadorJogador;
        this.nome = nome;
        this.valorFicha = valorFicha;
    }

    public Partida iniciarPartida(Jogo jogo, Jogador jogador, int quantidadeFichaAposta, int opcaoEscolhida) throws Exception {
        if (quantidadeFichaAposta <= 0) {
            throw new IllegalArgumentException("Quantidade de fichas apostadas deve ser positiva: " + quantidadeFichaAposta);
        }
        if (jogador.getCarteira().getFichas() < quantidadeFichaAposta) {
            throw new IllegalArgumentException("Jogador não possui fichas suficientes para apostar: " + quantidadeFichaAposta);
        }
        Partida partida = jogo.jogar(jogador,quantidadeFichaAposta, opcaoEscolhida);
        jogador.getPartidas().add(partida); 

        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();
          
            jogador.getCarteira().atualizarCarteiraNoBanco(con);
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Jogador jogadorAtualizado = gerenciadorJogador.buscarJogador(jogador.getNickname());
        jogador.setCarteira(jogadorAtualizado.getCarteira());

        //jogador.getPartidas().add(partida);
        daoPartidaGenerico.adicionar(partida);
        return partida;
    }

    public Jogo adicionarJogo(Jogo jogo) throws RegraDeNegocioException {
        if (jogoExiste(jogo.getNomeJogo())) {
            return daoJogoGenerico.buscar(jogo.getNomeJogo());
        }
        return daoJogoGenerico.adicionar(jogo);
    }
    public boolean removerJogo(String nomeJogo) throws NaoEncontradoException, RegraDeNegocioException {
        Jogo jogo = buscarJogo(nomeJogo);
        return daoJogoGenerico.remover(jogo);
    }

    public boolean jogoExiste(String nomeJogo) throws RegraDeNegocioException {
        return daoJogoGenerico.buscar(nomeJogo) != null;
    }

    public Jogo buscarJogo(String nomeJogo) throws NaoEncontradoException, RegraDeNegocioException {
        Jogo jogo = daoJogoGenerico.buscar(nomeJogo);
        if (jogo == null) {
            throw new NaoEncontradoException("Jogo com o nome " + nomeJogo + " não encontrado.");
        }
        return jogo;
    }

    public void comprarFicha(String nicknameJogador, int quantidadeFicha) throws Exception{
        if (quantidadeFicha <= 0) throw new IllegalArgumentException("Quantidade de fichas deve ser positiva: " + quantidadeFicha);

        Jogador jogador = gerenciadorJogador.buscarJogador(nicknameJogador);

        if(jogador.getCarteira().getDinheiro() < quantidadeFicha * valorFicha) throw new IllegalArgumentException("Jogador não possui dinheiro suficiente para comprar fichas.");

        jogador.getCarteira().depositarFichasCompradas(quantidadeFicha, getValorFicha());

        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();
            jogador.getCarteira().atualizarCarteiraNoBanco(con);
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Recarregar o jogador para atualizar a interface (carteira em memória)
        Jogador jogadorAtualizado = gerenciadorJogador.buscarJogador(nicknameJogador);
        jogador.setCarteira(jogadorAtualizado.getCarteira()); // Atualiza a carteira do objeto original
    }

    public void venderFicha(String nicknameJogador, int quantidadeFicha) throws Exception {
        if (quantidadeFicha <= 0) throw new IllegalArgumentException("Quantidade de fichas deve ser positiva: " + quantidadeFicha);

        // Busca o jogador (e sua carteira) do banco para garantir os dados mais recentes
        Jogador jogador = gerenciadorJogador.buscarJogador(nicknameJogador);

        if(!jogador.getCarteira().sacarFichasVendidas(quantidadeFicha, getValorFicha())) {
            throw new IllegalArgumentException("Jogador não possui "+ quantidadeFicha+ " fichas para vender.");
        }
        // Persistir a alteração no banco de dados
        Connection con = null;
        try {
            con = DataBaseSingleton.getConnection();
            jogador.getCarteira().atualizarCarteiraNoBanco(con);
        } catch (SQLException e) {
            throw new RegraDeNegocioException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Recarregar o jogador para atualizar a interface (carteira em memória)
        Jogador jogadorAtualizado = gerenciadorJogador.buscarJogador(nicknameJogador);
        jogador.setCarteira(jogadorAtualizado.getCarteira()); // Atualiza a carteira do objeto original
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