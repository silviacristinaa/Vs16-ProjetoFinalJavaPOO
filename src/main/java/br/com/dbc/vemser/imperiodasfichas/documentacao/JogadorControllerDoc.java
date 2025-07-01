package br.com.dbc.vemser.imperiodasfichas.documentacao;

import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorRankingDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorResponseDTO;
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

@Tag(name = "Jogador", description = "Operações relacionadas aos jogadores do sistema.")
public interface JogadorControllerDoc {

    @Operation(summary = "Listar ranking de jogadores", description = "Lista os jogadores ordenados pelo número de vitórias.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o ranking de jogadores."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @GetMapping("/ranking")
    ResponseEntity<List<JogadorRankingDTO>> getRanking() throws RegraDeNegocioException;

    @Operation(summary = "Listar jogadores", description = "Lista todos os jogadores cadastrados no sistema.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de jogadores."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @GetMapping
    ResponseEntity<List<JogadorResponseDTO>> listar() throws RegraDeNegocioException;

    @Operation(summary = "Buscar jogador por ID", description = "Busca um jogador específico pelo seu ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o jogador encontrado."),
                    @ApiResponse(responseCode = "404", description = "Jogador não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @GetMapping("/{idJogador}")
    ResponseEntity<JogadorResponseDTO> buscarPorId(@PathVariable("idJogador") Integer id) throws Exception;

    @Operation(summary = "Criar jogador", description = "Cadastra um novo jogador no sistema e cria sua carteira inicial.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do jogador recém-criado."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados enviados (e.g., nickname já existe, idade inválida)."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @PostMapping
    ResponseEntity<JogadorResponseDTO> criar(@RequestBody @Valid JogadorRequestDTO jogador) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar jogador", description = "Atualiza os dados de um jogador existente.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os dados do jogador atualizado."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados enviados."),
                    @ApiResponse(responseCode = "404", description = "Jogador não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @PutMapping("/{idJogador}")
    ResponseEntity<JogadorResponseDTO> atualizar(@PathVariable("idJogador") Integer id,
                                                 @RequestBody @Valid JogadorRequestDTO jogadorAtualizar) throws Exception;

    @Operation(summary = "Deletar jogador", description = "Remove um jogador do sistema, juntamente com sua carteira e histórico de partidas.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Jogador removido com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Jogador não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @DeleteMapping("/{idJogador}")
    ResponseEntity<Void> deletar(@PathVariable("idJogador") Integer id) throws Exception;
}
