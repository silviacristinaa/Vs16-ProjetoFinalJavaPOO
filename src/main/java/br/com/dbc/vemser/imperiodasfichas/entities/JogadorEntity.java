package br.com.dbc.vemser.imperiodasfichas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "id_jogador")
    private Integer idJogador;

    @Column(name = "nome")
    private String nome;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "idade")
    private int idade;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @OneToOne(mappedBy = "jogador", cascade = CascadeType.ALL, orphanRemoval = true)
    private CarteiraEntity carteira;

    @JsonIgnore
    @OneToMany(mappedBy = "jogador", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PartidaEntity> partidas;

    public JogadorEntity(Integer idJogador, String nome, String nickname, int idade, String email) {
        this.idJogador = idJogador;
        this.nome = nome;
        this.nickname = nickname;
        this.idade = idade;
        this.email = email;
    }

    public JogadorEntity(Integer idJogador) {
        this.idJogador = idJogador;
    }
}
