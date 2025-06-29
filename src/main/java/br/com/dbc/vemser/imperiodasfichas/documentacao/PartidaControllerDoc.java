package br.com.dbc.vemser.imperiodasfichas.documentacao;

import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface PartidaControllerDoc {

    @Operation(summary = "Listar partidas", description = "Lista o histórico de todas as partidas registradas no sistema.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de partidas."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @GetMapping
    ResponseEntity<List<PartidaResponseDTO>> listar() throws RegraDeNegocioException;

    @Operation(summary = "Buscar partida por ID", description = "Busca uma partida específica pelo seu ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna os detalhes da partida encontrada."),
                    @ApiResponse(responseCode = "404", description = "Partida não encontrada."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @GetMapping("/{idPartida}")
    ResponseEntity<PartidaResponseDTO> buscarPorId(@PathVariable("idPartida") Integer id) throws Exception;

    @Operation(summary = "Criar uma nova partida", description = "Registra uma nova partida no sistema. Geralmente é chamado internamente por um serviço de jogada.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Retorna os detalhes da partida recém-criada."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados enviados."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @PostMapping
    ResponseEntity<PartidaResponseDTO> criar(@RequestBody @Valid PartidaRequestDTO partida) throws Exception;

    @Operation(summary = "Deletar partida", description = "Remove o registro de uma partida do sistema.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Partida removida com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Partida não encontrada."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @DeleteMapping("/{idPartida}")
    ResponseEntity<Void> deletar(@PathVariable("idPartida") Integer id) throws Exception;
}
