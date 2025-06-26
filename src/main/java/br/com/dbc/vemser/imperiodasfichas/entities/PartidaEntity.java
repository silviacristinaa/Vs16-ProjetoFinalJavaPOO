package br.com.dbc.vemser.imperiodasfichas.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PartidaEntity {

    private Integer idPartida;
    private LocalDateTime dataHora;
    private int quantidadeFichasApostado;
    private boolean ganhou;
    private JogoEntity jogo;
    private JogadorEntity jogador;
}
