package br.com.dbc.vemser.imperiodasfichas.dtos;

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
    @Size(max = 50)
    private String nomeJogo;

    @Size(max = 500)
    private String regras;

    @NotNull
    private int valorInicial;
}
