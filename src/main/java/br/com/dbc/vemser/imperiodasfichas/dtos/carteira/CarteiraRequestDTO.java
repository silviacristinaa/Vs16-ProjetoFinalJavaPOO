package br.com.dbc.vemser.imperiodasfichas.dtos.carteira;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CarteiraRequestDTO {
    @NotNull
    @PositiveOrZero()
    @Schema(description = "Quantidade de fichas na carteira", example = "100")
    private Integer fichas = 10;

    @NotNull
    @PositiveOrZero()
    @Schema(description = "Quantidade de dinheiro na carteira", example = "50.00")
    private Double dinheiro = 0.0;
}
