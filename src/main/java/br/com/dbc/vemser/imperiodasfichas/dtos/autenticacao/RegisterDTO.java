package br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterDTO {

    @NotBlank
    @Schema(description = "Nome de usuário desejado", required = true, example = "usuario1")
    private String login;

    @NotBlank
    @Size(min = 6)
    @Schema(description = "Senha do usuário", required = true, example = "senhaSegura123")
    private String senha;

    @NotBlank
    @Schema(description = "Confirmação da senha", required = true, example = "senhaSegura123")
    private String confirmarSenha;

    @NotBlank
    @Schema(description = "Nome do cargo (ex: ADMIN, USUARIO)", required = true, example = "JOGADOR")
    private String nomeCargo;
}
