package br.com.dbc.vemser.imperiodasfichas.dtos.jogada;

import br.com.dbc.vemser.imperiodasfichas.enums.CoresDaRoletaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RoletaCoresRequestDTO {

    @NotBlank
    @NotNull
    @NotEmpty
    @Schema(description = "ID do Jogador", required = true, example = "1")
    private Integer idJogador;

    @NotBlank
    @NotNull
    @NotEmpty
    @Min(1)
    @Schema(description = "Quantidade de fichas a serem apostadas", required = true, example = "10")
    private Integer quantidadeFichasApostado;

    @NotBlank
    @NotNull
    @NotEmpty
    @Schema(description = "Opção de cor escolhida pelo jogador", required = true, example = "VERMELHO")
    private CoresDaRoletaEnum opcao;
}
