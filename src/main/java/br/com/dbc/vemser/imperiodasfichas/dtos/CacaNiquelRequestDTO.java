package br.com.dbc.vemser.imperiodasfichas.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CacaNiquelRequestDTO {

    @NotNull
    private Integer idJogador;

    @NotNull
    @Min(1)
    private Integer quantidadeFichasApostado;
}
