package br.com.dbc.vemser.imperiodasfichas.dtos;

import br.com.dbc.vemser.imperiodasfichas.enums.CoresDaRoletaEnum;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RoletaCoresRequestDTO {

    @NotNull
    private Integer idJogador;

    @NotNull
    @Min(1)
    private Integer quantidadeFichasApostado;

    @NotNull
    private CoresDaRoletaEnum opcao;
}
