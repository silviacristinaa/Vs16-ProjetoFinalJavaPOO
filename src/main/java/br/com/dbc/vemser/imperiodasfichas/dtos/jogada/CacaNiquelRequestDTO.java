package br.com.dbc.vemser.imperiodasfichas.dtos.jogada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class CacaNiquelRequestDTO {

    @NotBlank
    @Schema(description = "ID do Jogador", required = true, example = "1")
    private Integer idJogador;

    @NotBlank
    @Min(1)
    @Schema(description = "Quantidade de fichas a serem apostadas", required = true, example = "10")
    private Integer quantidadeFichasApostado;
}
