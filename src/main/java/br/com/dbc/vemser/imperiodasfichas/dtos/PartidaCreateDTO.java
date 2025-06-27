package br.com.dbc.vemser.imperiodasfichas.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class PartidaCreateDTO {
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
