package br.com.dbc.vemser.imperiodasfichas.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PARTIDA")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PartidaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARTIDA")
    @SequenceGenerator(name = "SEQ_PARTIDA", sequenceName = "SEQ_PARTIDA", allocationSize = 1)
    @Column(name = "id_partida")
    private Integer idPartida;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "fichas_apostadas", nullable = false)
    private Integer fichasApostadas;

    @Column(name = "ganhou", nullable = false, length = 1)
    private String ganhou; // 'S' ou 'N'

    // Correção crucial para Jogador
    @ManyToOne
    @JoinColumn(name = "id_jogador", referencedColumnName = "id_Jogador")
    private JogadorEntity jogador;

    // Correção crucial para Jogo
    @ManyToOne
    @JoinColumn(name = "id_jogo", referencedColumnName = "id")
    private JogoEntity jogo;
}
