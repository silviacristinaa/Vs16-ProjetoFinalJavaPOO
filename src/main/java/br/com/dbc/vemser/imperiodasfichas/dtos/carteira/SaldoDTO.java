package br.com.dbc.vemser.imperiodasfichas.dtos.carteira;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SaldoDTO {

    @Schema(description = "Quantidade de fichas atualizada na carteira", example = "90")
    private int fichas;

    @Schema(description = "Quantidade de dinheiro atualizada na carteira", example = "10.00")
    private double dinheiro;
}