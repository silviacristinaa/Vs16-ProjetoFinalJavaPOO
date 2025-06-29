package br.com.dbc.vemser.imperiodasfichas.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JogadorResponseDTO extends JogadorRequestDTO {
    @Schema(description = "ID único do jogador", example = "1")
    private Integer idJogador;

    @Schema(description = "ID da carteira associada ao jogador", example = "1")
    private Integer idCarteira;
}
