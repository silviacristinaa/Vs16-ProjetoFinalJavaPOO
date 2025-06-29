package br.com.dbc.vemser.imperiodasfichas.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PartidaResponseDTO extends PartidaRequestDTO {
    @Schema(description = "ID único da partida", example = "1")
    private Integer idPartida;
}
