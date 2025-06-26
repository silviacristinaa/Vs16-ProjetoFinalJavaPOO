package br.com.dbc.vemser.imperiodasfichas.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CarteiraResponseDTO extends CarteiraRequestDTO{
    @NotNull
    private Integer idCarteira;

    @NotNull
    private Integer idJogador;
}
