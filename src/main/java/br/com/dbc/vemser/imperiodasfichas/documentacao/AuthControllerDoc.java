package br.com.dbc.vemser.imperiodasfichas.documentacao;

import br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao.LoginDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao.RegisterDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao.TrocarSenhaDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao.UsuarioResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(
        name = "🔐 Autenticação e Usuários",
        description = """
        🔑 Endpoints para autenticação e gerenciamento de contas de usuários

        | Operação                   | Método HTTP | Descrição                                     |
        |----------------------------|-------------|-----------------------------------------------|
        | Login                      | POST        | Autentica o usuário e retorna um token JWT    |
        | Registrar usuário         | POST        | Cria novo usuário e autentica automaticamente |
        | Ativar usuário            | POST        | Ativa um usuário pelo login                   |
        | Desativar usuário         | POST        | Desativa um usuário pelo login                |
        | Trocar senha              | PATCH       | Atualiza a senha do usuário                   |
        | Buscar usuário logado    | GET         | Retorna os dados do usuário autenticado       |
        """
)
public interface AuthControllerDoc {

    @Operation(
            summary = "🔐 Login",
            description = "Autentica um usuário e retorna o token JWT"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ Sucesso - Token JWT gerado"),
            @ApiResponse(responseCode = "401", description = "❌ Erro - Credenciais inválidas")
    })
    String auth(
            @RequestBody @Valid LoginDTO loginDTO
    ) throws RegraDeNegocioException;

    @Operation(
            summary = "📝 Registrar usuário",
            description = "Cria um novo usuário no sistema e autentica automaticamente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ Sucesso - Token JWT gerado"),
            @ApiResponse(responseCode = "400", description = "❌ Erro - Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "❌ Erro - Falha ao autenticar após registro")
    })
    ResponseEntity<String> createUser(
            @RequestBody @Valid RegisterDTO registerDTO
    ) throws RegraDeNegocioException;

    @Operation(
            summary = "🚫 Desativar usuário",
            description = "Desativa o usuário com base no login informado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ Sucesso - Usuário desativado"),
            @ApiResponse(responseCode = "404", description = "❌ Erro - Usuário não encontrado")
    })
    ResponseEntity<String> desativarUsuario(
            @PathVariable String login
    ) throws RegraDeNegocioException;

    @Operation(
            summary = "✅ Ativar usuário",
            description = "Ativa o usuário com base no login informado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ Sucesso - Usuário ativado"),
            @ApiResponse(responseCode = "404", description = "❌ Erro - Usuário não encontrado")
    })
    ResponseEntity<String> ativarUsuario(
            @PathVariable String login
    ) throws RegraDeNegocioException;

    @Operation(
            summary = "🔄 Trocar senha",
            description = "Permite que o usuário troque sua senha"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ Sucesso - Senha atualizada"),
            @ApiResponse(responseCode = "400", description = "❌ Erro - Dados inválidos")
    })
    ResponseEntity<String> trocarSenha(
            @RequestBody @Valid TrocarSenhaDTO trocarSenhaDTO
    ) throws RegraDeNegocioException;

    @Operation(
            summary = "🙋‍♀️ Buscar usuário logado",
            description = "Retorna os dados do usuário atualmente autenticado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "✅ Sucesso - Dados do usuário logado"),
            @ApiResponse(responseCode = "403", description = "❌ Erro - Acesso negado (não autenticado)")
    })
    ResponseEntity<UsuarioResponseDTO> getLoggedUser() throws RegraDeNegocioException;

}
