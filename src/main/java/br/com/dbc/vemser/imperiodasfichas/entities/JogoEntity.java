package br.com.dbc.vemser.imperiodasfichas.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JogoEntity {
    private Integer idJogo;

    @NotNull
    @Size(max = 50)
    private String nomeJogo;

    @Size(max = 500)
    private String regras;

    @NotNull
    private int valorInicial;
}
