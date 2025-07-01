package br.com.dbc.vemser.imperiodasfichas.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartidaRequestDTO {
    @NotNull
    @Schema(description = "Data e hora da partida", required = true, example = "2025-06-17T14:00:00")
    private LocalDateTime dataHora;

    @NotNull
    @Schema(description = "Quantidade de fichas apostadas", required = true, example = "10")
    private Integer quantidadeFichasApostado;

    @NotNull
    @Schema(description = "Indica se o jogador ganhou a partida", required = true, example = "true")
    private Boolean ganhou;

    @NotNull
    @Schema(description = "ID do jogo da partida", required = true, example = "1")
    private Integer idJogo;

    @NotNull
    @Schema(description = "ID do jogador da partida", required = true, example = "1")
    private Integer idJogador;
}
