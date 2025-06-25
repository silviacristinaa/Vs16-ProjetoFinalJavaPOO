package br.com.dbc.vemser.imperiodasfichas.entities;

import java.util.HashSet;
import java.util.Set;

public class JogadorEntity {

    private Integer idJogador;
    private String nome;
    private String nickname;
    private int idade;
    private CarteiraEntity carteira;
    private Set<PartidaEntity> partidas;

    public Integer getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Integer idJogador) {
        this.idJogador = idJogador;
    }

    public JogadorEntity() {
        this.partidas = new HashSet<>();
    }

    public JogadorEntity(String nome, int idade, String nickname) {
        this.nome = nome;
        this.idade = idade;
        this.nickname = nickname;
        this.carteira = new CarteiraEntity();
        this.partidas = new HashSet<>();
    }
    public JogadorEntity(String nome, int idade, String nickname, CarteiraEntity carteira) {
        this.nome = nome;
        this.idade = idade;
        this.nickname = nickname;
        this.carteira = carteira;
        this.partidas = new HashSet<>();
    }

    public String getNome() {
        return nome;
    }

    public CarteiraEntity getCarteira() {
        return carteira;
    }

    public int getIdade() {
        return idade;
    }

    public String getNickname() {
        return nickname;
    }

    public Set<PartidaEntity> getPartidas() {
        return partidas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setCarteira(CarteiraEntity carteira) {
        this.carteira = carteira;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return ">> Nickname atual: " + nickname + "\n>> Nome atual: " + nome + "\n>> Idade atual: " + idade;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JogadorEntity other = (JogadorEntity) obj;
        if (nickname == null) {
            if (other.nickname != null)
                return false;
        } else if (!nickname.equalsIgnoreCase(other.nickname))
            return false;
        return true;
    }
}
