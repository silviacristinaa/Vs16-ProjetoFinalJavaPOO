package br.com.dbc.vemser.imperiodasfichas.dtos;

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
    private LocalDateTime dataHora;
    @NotNull
    private Integer quantidadeFichasApostado;
    @NotNull
    private Boolean ganhou;
    @NotNull
    private Integer idJogo;
    @NotNull
    private Integer idJogador;
}
