package br.com.dbc.vemser.imperiodasfichas.dtos;

import br.com.dbc.vemser.imperiodasfichas.enums.NomeJogoEnum;
import lombok.Data;

@Data
public class JogoResponseDTO {
    private Integer idJogo;
    private String nomeJogo;
    private String regras;
    private int valorInicial;

    public void setNomeJogo(NomeJogoEnum nomeJogo) {
        this.nomeJogo = nomeJogo.getNome();
    }
}
