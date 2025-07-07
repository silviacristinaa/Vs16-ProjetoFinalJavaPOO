package br.com.dbc.vemser.imperiodasfichas.documentacao;

import br.com.dbc.vemser.imperiodasfichas.dtos.JogoRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.JogoResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Jogo", description = "Operações relacionadas aos jogos do sistema.")
public interface JogoControllerDoc {

    @Operation(summary = "Listar jogos", description = "Lista todos os jogos disponíveis no sistema.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de jogos."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @GetMapping
    ResponseEntity<List<JogoResponseDTO>> list() throws RegraDeNegocioException;

    @Operation(summary = "Buscar jogo por ID", description = "Busca um jogo específico pelo seu ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os detalhes do jogo encontrado."),
                    @ApiResponse(responseCode = "404", description = "Jogo não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<JogoResponseDTO> findById(@PathVariable("id") Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Criar um novo jogo", description = "Cadastra um novo jogo no sistema.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os detalhes do jogo recém-criado."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados enviados."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @PostMapping
    ResponseEntity<JogoResponseDTO> create(@Valid @RequestBody JogoRequestDTO jogo) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar um jogo", description = "Atualiza as informações de um jogo existente.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os detalhes do jogo atualizado."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados enviados."),
                    @ApiResponse(responseCode = "404", description = "Jogo não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<JogoResponseDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody JogoRequestDTO jogoAtualizar) throws RegraDeNegocioException;

    @Operation(summary = "Deletar um jogo", description = "Remove um jogo do sistema.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Jogo removido com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Jogo não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException;
}
