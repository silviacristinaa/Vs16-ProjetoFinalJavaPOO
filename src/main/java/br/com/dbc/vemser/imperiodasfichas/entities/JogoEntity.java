package br.com.dbc.vemser.imperiodasfichas.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JogoEntity {
    private Integer idJogo;
    private String nomeJogo;
    private String regras;
    private int valorInicial;
}
