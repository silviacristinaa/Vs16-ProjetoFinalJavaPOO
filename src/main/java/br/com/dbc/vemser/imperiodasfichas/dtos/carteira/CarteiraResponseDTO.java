package br.com.dbc.vemser.imperiodasfichas.dtos.carteira;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CarteiraResponseDTO extends CarteiraRequestDTO {
    @NotBlank
    @NotNull
    @NotEmpty
    @Schema(description = "ID único da carteira", example = "1")
    private Integer idCarteira;

    @NotBlank
    @NotNull
    @NotEmpty
    @Schema(description = "ID do jogador associado à carteira", example = "1")
    private Integer idJogador;
}
