//package br.com.dbc.vemser.imperiodasfichas.documentacao;
//
//import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaResponseDTO;
//import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//
//@Tag(name = "Partida", description = "Operações relacionadas às partidas jogadas no sistema.")
//public interface PartidaControllerDoc {
//
//    @Operation(summary = "Listar partidas", description = "Lista o histórico de todas as partidas registradas no sistema.")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Retorna a lista de partidas."),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
//            }
//    )
//    @GetMapping
//    ResponseEntity<List<PartidaResponseDTO>> listar() throws RegraDeNegocioException;
//
//    @Operation(summary = "Buscar partida por ID", description = "Busca uma partida específica pelo seu ID.")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Retorna os detalhes da partida encontrada."),
//                    @ApiResponse(responseCode = "404", description = "Partida não encontrada."),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
//            }
//    )
//    @GetMapping("/{idPartida}")
//    ResponseEntity<PartidaResponseDTO> buscarPorId(@PathVariable("idPartida") Integer id) throws Exception;
//
//    @Operation(summary = "Deletar partida", description = "Remove o registro de uma partida do sistema.")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Partida removida com sucesso."),
//                    @ApiResponse(responseCode = "404", description = "Partida não encontrada."),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
//            }
//    )
//    @DeleteMapping("/{idPartida}")
//    ResponseEntity<Void> deletar(@PathVariable("idPartida") Integer id) throws Exception;
//}




package br.com.dbc.vemser.imperiodasfichas.documentacao;

import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "🎮 Partida",
        description = """
        🕹️ Endpoints para gerenciamento do histórico de partidas jogadas

        | Operação                | Método HTTP | Descrição                            |
        |-------------------------|-------------|----------------------------------------|
        | Listar partidas         | GET         | Retorna todas as partidas              |
        | Listar partidas (página)| GET         | Retorna partidas paginadas             |
        | Buscar por ID           | GET         | Retorna uma partida específica         |
        | Deletar partida         | DELETE      | Remove uma partida                     |
        """
)
public interface PartidaControllerDoc {

    @Operation(
            summary = "📄 Listar partidas",
            description = "Retorna todas as partidas registradas no sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "✅ Sucesso - Lista de partidas",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = """
                    [
                        {
                            "idPartida": 1,
                            "nomeJogador": "Luke Skywalker",
                            "nomeJogo": "X-Wing Battles",
                            "pontuacao": 2500,
                            "dataPartida": "2025-07-06T14:00:00",
                            "ganhou": true
                        }
                    ]
                    """)
            )
    )
    ResponseEntity<List<PartidaResponseDTO>> listar() throws RegraDeNegocioException;

    @Operation(
            summary = "🔎 Buscar partida por ID",
            description = "Busca os detalhes de uma partida específica pelo seu ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "✅ Sucesso - Partida encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "idPartida": 1,
                                "nomeJogador": "Leia Organa",
                                "nomeJogo": "Rebellion Run",
                                "pontuacao": 1800,
                                "dataPartida": "2025-07-05T20:30:00",
                                "ganhou": false
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "❌ Erro - Partida não encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Partida não encontrada",
                                "details": "Nenhuma partida com ID 999 foi localizada",
                                "timestamp": "2025-07-08T10:00:00"
                            }
                            """)
                    )
            )
    })
    ResponseEntity<PartidaResponseDTO> buscarPorId(
            @PathVariable("idPartida") Integer id
    ) throws Exception;

    @Operation(
            summary = "🗑️ Deletar partida",
            description = "Remove uma partida do sistema com base no ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "✅ Sucesso - Partida deletada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "❌ Erro - Partida não encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Partida não encontrada",
                                "details": "Nenhuma partida com ID 999 foi localizada",
                                "timestamp": "2025-07-08T10:01:00"
                            }
                            """)
                    )
            )
    })
    ResponseEntity<Void> deletar(
            @PathVariable("idPartida") Integer id
    ) throws Exception;

    @Operation(
            summary = "📄 Paginação de partidas",
            description = "Lista as partidas registradas no sistema com suporte a paginação"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "✅ Sucesso - Lista paginada de partidas",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "content": [
                                    {
                                        "idPartida": 1,
                                        "nomeJogador": "Han Solo",
                                        "nomeJogo": "Falcon Run",
                                        "pontuacao": 3100,
                                        "dataPartida": "2025-07-07T22:00:00",
                                        "ganhou": true
                                    }
                                ],
                                "pageable": {
                                    "pageNumber": 0,
                                    "pageSize": 10
                                },
                                "totalPages": 1,
                                "totalElements": 1
                            }
                            """)
                    )
            )
    })
    @GetMapping("/paginado")
    ResponseEntity<Page<PartidaResponseDTO>> listarPaginado(
            @Parameter(description = "Número da página que deseja visualizar", example = "0")
            @RequestParam(defaultValue = "0") int pagina,

            @Parameter(description = "Quantidade de registros por página", example = "10")
            @RequestParam(defaultValue = "10") int tamanhoPagina
    ) throws RegraDeNegocioException;
}
