package br.com.dbc.vemser.imperiodasfichas.dtos.jogada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RoletaParImparResponseDTO extends JogadaResponseBaseDTO {
    @Schema(description = "Número sorteada no jogo da roleta clássica", example = "10")
    private int numeroSorteado;
}
