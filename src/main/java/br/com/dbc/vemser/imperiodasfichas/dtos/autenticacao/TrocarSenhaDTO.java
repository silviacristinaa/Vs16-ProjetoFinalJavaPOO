package br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TrocarSenhaDTO {

    @NotNull
    @Schema(description = "Senha atual do usuário", required = true, example = "senhaSegura")
    private String senhaAtual;

    @NotBlank
    @Size(min = 3)
    @Schema(description = "Nova senha do usuário", required = true, example = "senhaSegura123")
    private String novaSenha;

    @NotBlank
    @Schema(description = "Confirmação da nova senha", required = true, example = "senhaSegura123")
    private String confirmarNovaSenha;
}
