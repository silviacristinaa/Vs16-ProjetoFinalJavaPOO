package br.com.dbc.vemser.imperiodasfichas.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CarteiraRequestDTO {
    @PositiveOrZero()
    @Schema(description = "Quantidade de fichas na carteira", example = "100")
    private int fichas = 10;

    @PositiveOrZero()
    @Schema(description = "Quantidade de dinheiro na carteira", example = "50.00")
    private double dinheiro = 0;
}
