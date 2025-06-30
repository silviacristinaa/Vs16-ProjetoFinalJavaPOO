package br.com.dbc.vemser.imperiodasfichas.dtos.jogada;

import br.com.dbc.vemser.imperiodasfichas.enums.CoresDaRoletaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RoletaCoresResponseDTO extends JogadaResponseBaseDTO {
    @Schema(description = "Cor sorteada no jogo da roleta das cores", example = "AMARELO")
    private CoresDaRoletaEnum corSorteada;
}
