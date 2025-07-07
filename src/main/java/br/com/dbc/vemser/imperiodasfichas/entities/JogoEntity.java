package br.com.dbc.vemser.imperiodasfichas.entities;

import br.com.dbc.vemser.imperiodasfichas.enums.NomeJogoEnum;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "JOGO")
public class JogoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOGO_SEQ")
    @SequenceGenerator(name = "JOGO_SEQ", sequenceName = "seq_jogo", allocationSize = 1)
    @Column(name = "id")
    private Integer idJogo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nome_jogo")
    private NomeJogoEnum nomeJogo;

    @Column(name = "regras")
    private String regras;

    @Column(name = "valor_inicial")
    private int valorInicial;

    public JogoEntity(Integer idJogo) {
        this.idJogo = idJogo;
    }
}
