package br.com.dbc.vemser.imperiodasfichas.dtos;

import lombok.Data;

@Data
public class JogoResponseDTO {
    private Integer idJogo;
    private String nomeJogo;
    private String regras;
    private int valorInicial;
}
