package br.com.dbc.vemser.imperiodasfichas.dtos;

import br.com.dbc.vemser.imperiodasfichas.enums.NomeJogoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JogoResponseDTO {
    @Schema(description = "ID único do jogo", example = "1")
    private Integer idJogo;

    @Schema(description = "Nome do jogo", example = "Roleta Clássica")
    private String nomeJogo;

    @Schema(description = "Regras do jogo", example = "Aposte em PAR ou ÍMPAR. Se acertar, ganha o dobro do valor apostado!")
    private String regras;

    @Schema(description = "Valor mínimo de aposta para o jogo", example = "5")
    private int valorInicial;

//    public void setNomeJogo(NomeJogoEnum nomeJogo) {
//        this.nomeJogo = nomeJogo.getNome();
//    }
}
