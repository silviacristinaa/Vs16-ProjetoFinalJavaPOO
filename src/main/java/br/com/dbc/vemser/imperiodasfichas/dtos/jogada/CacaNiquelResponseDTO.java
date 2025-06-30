package br.com.dbc.vemser.imperiodasfichas.dtos.jogada;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CacaNiquelResponseDTO extends JogadaResponseBaseDTO {
    @ArraySchema(schema = @Schema(description = "Símbolos sorteados no jogo caça níquel", example = "[\"🍒\", \"🍒\", \"🍒\"]"))
    private List<String> simbolosSorteados;
}
