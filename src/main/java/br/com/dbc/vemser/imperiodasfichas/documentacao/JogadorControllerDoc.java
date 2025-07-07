package br.com.dbc.vemser.imperiodasfichas.documentacao;

import br.com.dbc.vemser.imperiodasfichas.dtos.RelatorioJogadorSimplesDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorRankingDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.JogadorResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Jogador", description = "Operações relacionadas aos jogadores do sistema.")
public interface JogadorControllerDoc {

//    @Operation(summary = "Listar ranking de jogadores", description = "Lista os jogadores ordenados pelo número de vitórias.")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Retorna o ranking de jogadores."),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
//            }
//    )
//    @GetMapping("/ranking")
//    ResponseEntity<List<JogadorRankingDTO>> getRanking() throws RegraDeNegocioException;

    @Operation(summary = "Listar jogadores", description = "Lista todos os jogadores cadastrados no sistema.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de jogadores."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @GetMapping
    ResponseEntity<List<JogadorResponseDTO>> list() throws RegraDeNegocioException;

    @Operation(summary = "Buscar jogador por ID", description = "Busca um jogador específico pelo seu ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o jogador encontrado."),
                    @ApiResponse(responseCode = "404", description = "Jogador não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @GetMapping("/{idJogador}")
    ResponseEntity<JogadorResponseDTO> findById(@PathVariable("idJogador") Integer idJogador) throws RegraDeNegocioException;

    @Operation(summary = "Criar jogador", description = "Cadastra um novo jogador no sistema e cria sua carteira inicial.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os dados do jogador recém-criado."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados enviados (e.g., nickname já existe, idade inválida)."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @PostMapping
    ResponseEntity<JogadorResponseDTO> create(@RequestBody @Valid JogadorRequestDTO jogador) throws RegraDeNegocioException;

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
    ResponseEntity<JogadorResponseDTO> update(@PathVariable("idJogador") Integer idJogador,
                                              @RequestBody @Valid JogadorRequestDTO jogadorAtualizar) throws RegraDeNegocioException;

    @Operation(summary = "Deletar jogador", description = "Remove um jogador do sistema, juntamente com sua carteira e histórico de partidas.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Jogador removido com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Jogador não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @DeleteMapping("/{idJogador}")
    ResponseEntity<Void> delete(@PathVariable("idJogador") Integer idJogador) throws RegraDeNegocioException;

    @Operation(summary = "Relatório simplificado de jogadores",
            description = "Gera um relatório com dados básicos de todos os jogadores e suas carteiras")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro ao processar o relatório")
            }
    )
    @GetMapping("/relatorio-simples")
    ResponseEntity<List<RelatorioJogadorSimplesDTO>> getRelatorioSimples() throws RegraDeNegocioException;

    @Operation(summary = "Relatório paginado de jogadores",
            description = "Gera um relatório paginado com dados básicos dos jogadores e suas carteiras")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Relatório paginado gerado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros de paginação inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro ao processar o relatório")
            }
    )
    @GetMapping("/relatorio-simples/paginado")
    ResponseEntity<Page<RelatorioJogadorSimplesDTO>> getRelatorioSimplesPaginado(
            @Parameter(description = "Número da página (0-based)", example = "0")
            @RequestParam(defaultValue = "0") Integer pagina,

            @Parameter(description = "Quantidade de itens por página", example = "10")
            @RequestParam(defaultValue = "10") Integer tamanho) throws RegraDeNegocioException;
}


