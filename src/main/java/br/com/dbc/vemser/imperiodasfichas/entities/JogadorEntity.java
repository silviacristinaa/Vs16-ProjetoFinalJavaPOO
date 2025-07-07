package br.com.dbc.vemser.imperiodasfichas.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;
import javax.persistence.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name= "JOGADOR")
@Table(name = "JOGADOR")
public class JogadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOGADOR_SEQ")
    @SequenceGenerator(name = "JOGADOR_SEQ", sequenceName = "seq_jogador", allocationSize = 1)

    @Column(name = "idJogador")
    private Integer idJogador;

    @Column(name = "nome")
    private String nome;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "idade")
    private int idade;

    @Column(name = "email")
    private String email;

    //private CarteiraEntity carteira; //posso comentar a carteira
    //private Set<PartidaEntity> partidas;

//    public JogadorEntity(Integer idJogador, String nome, String nickname, int idade, String email) {
//        this.idJogador = idJogador;
//        this.nome = nome;
//        this.nickname = nickname;
//        this.idade = idade;
//        this.email = email;
//    }

    public JogadorEntity(Integer idJogador) {
        this.idJogador = idJogador;
    }
}
