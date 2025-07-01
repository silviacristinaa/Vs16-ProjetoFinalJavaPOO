package br.com.dbc.vemser.imperiodasfichas.dtos.carteira;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CarteiraRequestDTO {
    @NotBlank
    @NotNull
    @NotEmpty
    @PositiveOrZero()
    @Schema(description = "Quantidade de fichas na carteira", example = "100")
    private int fichas = 10;

    @NotBlank
    @NotNull
    @NotEmpty
    @PositiveOrZero()
    @Schema(description = "Quantidade de dinheiro na carteira", example = "50.00")
    private double dinheiro = 0;
}
