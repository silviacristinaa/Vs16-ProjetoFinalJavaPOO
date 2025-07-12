package br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {

    @NotNull
    @Schema(description = "Nome de usuário utilizado para login", required = true, example = "user")
    private String login;

    @NotNull
    @Schema(description = "Senha do usuário", required = true, example = "senhaSegura")
    private String senha;
}
