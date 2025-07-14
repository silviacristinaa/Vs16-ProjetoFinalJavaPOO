package br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AtualizarUsuarioDTO {
    @Schema(description = "Login do usuário", example = "usuario1")
    private String login;

    @Schema(description = "Cargo do usuário", example = "USUARIO")
    private String nomeCargo;
}
