package br.com.dbc.vemser.imperiodasfichas.dtos;

import br.com.dbc.vemser.imperiodasfichas.enums.NomeJogoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogoRequestDTO {
    @NotNull
    private NomeJogoEnum nomeJogo;

    @Size(max = 500)
    private String regras;

    @NotNull
    private int valorInicial;
}
