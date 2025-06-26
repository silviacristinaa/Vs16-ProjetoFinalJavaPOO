package br.com.dbc.vemser.imperiodasfichas.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartidaCreateDTO {
    private LocalDateTime dataHora;
    private int quantidadeFichasApostado;
    private boolean ganhou;
    private int idJogo;
    private int idJogador;
}
