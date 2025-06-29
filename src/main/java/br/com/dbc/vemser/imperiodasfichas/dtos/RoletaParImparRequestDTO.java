package br.com.dbc.vemser.imperiodasfichas.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RoletaParImparRequestDTO {

    @NotNull
    @Schema(description = "ID do Jogador", required = true, example = "1")
    private Integer idJogador;

    @NotNull
    @Min(1)
    @Schema(description = "Quantidade de fichas a serem apostadas", required = true, example = "10")
    private Integer quantidadeFichasApostado;

    @NotNull
    @Min(value = 0, message = "Valor deve ser 0 para par ou 1 para impar")
    @Max(value = 1, message = "Valor deve ser 0 para par ou 1 para impar")
    @Schema(description = "Opção de aposta (0 para PAR, 1 para ÍMPAR)", required = true, example = "0")
    private Integer opcao;
}
