package br.com.dbc.vemser.imperiodasfichas.entities;

import br.com.dbc.vemser.imperiodasfichas.enums.NomeJogoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JogoEntity {
    private Integer idJogo;
    private NomeJogoEnum nomeJogo;
    private String regras;
    private int valorInicial;

    public JogoEntity(Integer idJogo) {
        this.idJogo = idJogo;
    }
}
