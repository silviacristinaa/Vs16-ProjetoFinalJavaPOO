package br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class UsuarioResponseDTO {

    @Schema(description = "ID único do usuário", example = "1")
    private Integer idUsuario;

    @Schema(description = "Login do usuário", example = "usuario1")
    private String login;

    @Schema(description = "Status do usuário ('S': ativo, 'N': desativado)", example = "S")
    private String ativo;

    @Schema(description = "Nome do cargo (ex: ADMIN, USUARIO)", example = "USUARIO")
    private Set<String>nomeCargo;
}
