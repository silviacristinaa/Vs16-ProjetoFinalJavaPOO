package br.com.dbc.vemser.imperiodasfichas.dtos.jogada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RoletaParImparRequestDTO {

    @NotBlank
    @NotNull
    @NotEmpty
    @Schema(description = "ID do Jogador", required = true, example = "1")
    private Integer idJogador;

    @NotBlank
    @NotNull
    @NotEmpty
    @Min(1)
    @Schema(description = "Quantidade de fichas a serem apostadas", required = true, example = "10")
    private Integer quantidadeFichasApostado;

    @NotBlank
    @NotNull
    @NotEmpty
    @Min(value = 0, message = "Valor deve ser 0 para par ou 1 para impar")
    @Max(value = 1, message = "Valor deve ser 0 para par ou 1 para impar")
    @Schema(description = "Opção de aposta (0 para PAR, 1 para ÍMPAR)", required = true, example = "0")
    private Integer opcao;
}
