package br.com.dbc.vemser.imperiodasfichas.dtos.jogada;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JogadaResponseBaseDTO {
    @Schema(description = "Indica se o jogador ganhou a partida", example = "true")
    private boolean ganhou;

    @Schema(description = "Quantidade de fichas recebidas como prêmio", example = "10")
    private int fichasRecebidas;

    @Schema(description = "Saldo final de fichas do jogador", example = "10")
    private int saldoFinalDeFichas;
}
