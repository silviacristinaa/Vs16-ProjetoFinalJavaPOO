package br.com.dbc.vemser.imperiodasfichas.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JogadorRankingDTO {

    @Schema(description = "Posição do jogador no ranking")
    private Integer rank;

    @Schema(description = "Nickname do jogador")
    private String nickname;

    @Schema(description = "Total de vitórias do jogador")
    private Integer vitorias;

    @Schema(description = "ID do jogador")
    private Integer idJogador;
}