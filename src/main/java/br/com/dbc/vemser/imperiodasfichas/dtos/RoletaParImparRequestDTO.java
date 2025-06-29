package br.com.dbc.vemser.imperiodasfichas.dtos;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RoletaParImparRequestDTO {

    @NotNull
    private Integer idJogador;

    @NotNull
    @Min(1)
    private Integer quantidadeFichasApostado;

    @NotNull
    @Min(value = 0, message = "Valor deve ser 0 para par ou 1 para impar")
    @Max(value = 1, message = "Valor deve ser 0 para par ou 1 para impar")
    private Integer opcao;
}
