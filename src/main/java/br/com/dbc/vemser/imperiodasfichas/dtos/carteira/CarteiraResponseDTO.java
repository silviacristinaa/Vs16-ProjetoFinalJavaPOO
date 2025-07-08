package br.com.dbc.vemser.imperiodasfichas.dtos.carteira;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CarteiraResponseDTO extends CarteiraRequestDTO {
    @Schema(description = "ID único da carteira", example = "1")
    private Integer idCarteira;

    @Schema(description = "ID do jogador associado à carteira", example = "1")
    private Integer idJogador;
}
