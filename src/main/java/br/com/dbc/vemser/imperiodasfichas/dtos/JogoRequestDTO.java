package br.com.dbc.vemser.imperiodasfichas.dtos;

import br.com.dbc.vemser.imperiodasfichas.enums.NomeJogoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogoRequestDTO {
    @NotBlank
    @Schema(description = "Nome do jogo", required = true, example = "ROLETA_CLASSICA")
    private NomeJogoEnum nomeJogo;

    @NotBlank
    @Size(max = 500)
    @Schema(description = "Regras do jogo", example = "Aposte em PAR ou ÍMPAR. Se acertar, ganha o dobro do valor apostado!")
    private String regras;

    @NotBlank
    @Schema(description = "Valor mínimo de aposta para o jogo", required = true, example = "5")
    private int valorInicial;
}
