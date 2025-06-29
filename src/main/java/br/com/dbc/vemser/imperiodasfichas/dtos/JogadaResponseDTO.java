package br.com.dbc.vemser.imperiodasfichas.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JogadaResponseDTO {

    @Schema(description = "Indica se o jogador ganhou a partida", example = "true")
    private boolean ganhou;
}
