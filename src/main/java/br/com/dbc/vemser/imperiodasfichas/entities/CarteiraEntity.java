package br.com.dbc.vemser.imperiodasfichas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "CARTEIRA")
public class CarteiraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARTEIRA_SEQ")
    @SequenceGenerator(name = "CARTEIRA_SEQ", sequenceName = "seq_carteira", allocationSize = 1)
    @Column(name = "id")
    private Integer idCarteira;

    @Column(name = "fichas")
    private int fichas;

    @Column(name = "dinheiro")
    private double dinheiro;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jogador", referencedColumnName = "ID_JOGADOR")
    private JogadorEntity jogador;
}
