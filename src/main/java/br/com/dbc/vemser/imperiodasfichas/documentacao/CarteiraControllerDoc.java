package br.com.dbc.vemser.imperiodasfichas.documentacao;

import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.CarteiraRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.CarteiraResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.SaldoDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(
        name = "💰 Carteira",
        description = """
        🏦 Endpoints para gerenciamento das carteiras virtuais dos jogadores

        | Operação                | Método HTTP | Descrição                            |
        |-------------------------|-------------|--------------------------------------|
        | Listar carteiras        | GET         | Retorna todas as carteiras           |
        | Buscar por ID           | GET         | Busca carteira por ID                |
        | Buscar por jogador      | GET         | Busca carteira de um jogador         |
        | Atualizar carteira      | PUT         | Atualiza dados da carteira           |
        | Depositar dinheiro      | PUT         | Adiciona dinheiro à carteira         |
        | Sacar dinheiro          | PUT         | Retira dinheiro da carteira          |
        | Comprar fichas          | PUT         | Converte dinheiro em fichas          |
        | Vender fichas           | PUT         | Converte fichas em dinheiro          |
        """
)
public interface CarteiraControllerDoc {

    @Operation(
            summary = "📄 Listar carteiras",
            description = "Retorna todas as carteiras cadastradas no sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "✅ Sucesso - Lista de carteiras retornada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = """
                    [
                        {
                            "idCarteira": 1,
                            "idJogador": 101,
                            "saldoDinheiro": 1500.50,
                            "quantidadeFichas": 25,
                            "ultimaAtualizacao": "2025-07-08T10:00:00"
                        },
                        {
                            "idCarteira": 2,
                            "idJogador": 102,
                            "saldoDinheiro": 800.75,
                            "quantidadeFichas": 40,
                            "ultimaAtualizacao": "2025-07-08T10:05:00"
                        }
                    ]
                    """)
            )
    )
    ResponseEntity<List<CarteiraResponseDTO>> listar() throws RegraDeNegocioException;

    @Operation(
            summary = "🔍 Buscar carteira por ID",
            description = "Busca uma carteira específica pelo seu ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "✅ Sucesso - Carteira encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "idCarteira": 1,
                                "idJogador": 101,
                                "saldoDinheiro": 1500.50,
                                "quantidadeFichas": 25,
                                "ultimaAtualizacao": "2025-07-08T10:00:00"
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "❌ Erro - Carteira não encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Carteira não encontrada",
                                "details": "Nenhuma carteira com ID 999 foi localizada",
                                "timestamp": "2025-07-08T10:10:00"
                            }
                            """)
                    )
            )
    })
    ResponseEntity<CarteiraResponseDTO> buscarPorId(
            @PathVariable("idCarteira") Integer id
    ) throws RegraDeNegocioException;

    @Operation(
            summary = "👤 Buscar carteira por jogador",
            description = "Busca a carteira associada a um jogador específico"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "✅ Sucesso - Carteira do jogador encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "idCarteira": 3,
                                "idJogador": 103,
                                "saldoDinheiro": 2000.00,
                                "quantidadeFichas": 50,
                                "ultimaAtualizacao": "2025-07-08T10:15:00"
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "❌ Erro - Jogador ou carteira não encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Jogador não encontrado",
                                "details": "Nenhum jogador com ID 999 foi localizado",
                                "timestamp": "2025-07-08T10:20:00"
                            }
                            """)
                    )
            )
    })
    ResponseEntity<CarteiraResponseDTO> buscarPorIdJogador(
            @PathVariable("idJogador") Integer idJogador
    ) throws RegraDeNegocioException;

    @Operation(
            summary = "🔄 Atualizar carteira",
            description = "Atualiza os dados de uma carteira existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "✅ Sucesso - Carteira atualizada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "idCarteira": 1,
                                "idJogador": 101,
                                "saldoDinheiro": 2000.00,
                                "quantidadeFichas": 30,
                                "ultimaAtualizacao": "2025-07-08T10:25:00"
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "❌ Erro - Dados inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "errors": [
                                    "saldoDinheiro: deve ser positivo",
                                    "quantidadeFichas: deve ser positivo"
                                ],
                                "message": "Dados inválidos",
                                "timestamp": "2025-07-08T10:30:00"
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "❌ Erro - Carteira não encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Carteira não encontrada",
                                "details": "Nenhuma carteira com ID 999 foi localizada",
                                "timestamp": "2025-07-08T10:35:00"
                            }
                            """)
                    )
            )
    })
    ResponseEntity<CarteiraResponseDTO> atualizar(
            @PathVariable("idCarteira") Integer id,
            @RequestBody @Valid CarteiraRequestDTO carteiraAtualizarDTO
    ) throws RegraDeNegocioException;

    @Operation(
            summary = "💵 Depositar dinheiro",
            description = "Adiciona um valor ao saldo de dinheiro da carteira"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "✅ Sucesso - Depósito realizado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "saldoDinheiro": 2500.50,
                                "quantidadeFichas": 25,
                                "mensagem": "Depósito realizado com sucesso"
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "❌ Erro - Valor inválido",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Valor inválido",
                                "details": "O valor do depósito deve ser positivo",
                                "timestamp": "2025-07-08T10:40:00"
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "❌ Erro - Carteira não encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Carteira não encontrada",
                                "details": "Nenhuma carteira com ID 999 foi localizada",
                                "timestamp": "2025-07-08T10:45:00"
                            }
                            """)
                    )
            )
    })
    ResponseEntity<SaldoDTO> depositarDinheiro(
            @PathVariable("idCarteira") Integer id,
            @Parameter(description = "Valor a ser depositado", example = "500.00")
            @RequestParam double valor
    ) throws RegraDeNegocioException;

    @Operation(
            summary = "🏧 Sacar dinheiro",
            description = "Retira um valor do saldo de dinheiro da carteira"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "✅ Sucesso - Saque realizado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "saldoDinheiro": 1000.50,
                                "quantidadeFichas": 25,
                                "mensagem": "Saque realizado com sucesso"
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "❌ Erro - Valor inválido ou saldo insuficiente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Saldo insuficiente",
                                "details": "Saldo atual: 500.00 | Valor solicitado: 1000.00",
                                "timestamp": "2025-07-08T10:50:00"
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "❌ Erro - Carteira não encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Carteira não encontrada",
                                "details": "Nenhuma carteira com ID 999 foi localizada",
                                "timestamp": "2025-07-08T10:55:00"
                            }
                            """)
                    )
            )
    })
    ResponseEntity<SaldoDTO> sacarDinheiro(
            @PathVariable("idCarteira") Integer id,
            @Parameter(description = "Valor a ser sacado", example = "500.00")
            @RequestParam double valor
    ) throws RegraDeNegocioException;

    @Operation(
            summary = "🎟️ Comprar fichas",
            description = "Converte dinheiro em fichas usando a cotação atual"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "✅ Sucesso - Fichas compradas",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "saldoDinheiro": 1300.00,
                                "quantidadeFichas": 35,
                                "mensagem": "Compra de fichas realizada com sucesso"
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "❌ Erro - Quantidade inválida ou dinheiro insuficiente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Dinheiro insuficiente",
                                "details": "Saldo atual: 500.00 | Valor necessário: 1000.00",
                                "timestamp": "2025-07-08T11:00:00"
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "❌ Erro - Carteira não encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Carteira não encontrada",
                                "details": "Nenhuma carteira com ID 999 foi localizada",
                                "timestamp": "2025-07-08T11:05:00"
                            }
                            """)
                    )
            )
    })
    ResponseEntity<SaldoDTO> comprarFichas(
            @PathVariable("idCarteira") Integer id,
            @Parameter(description = "Quantidade de fichas a comprar", example = "10")
            @RequestParam int quantidade
    ) throws RegraDeNegocioException;

    @Operation(
            summary = "💲 Vender fichas",
            description = "Converte fichas em dinheiro usando a cotação atual"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "✅ Sucesso - Fichas vendidas",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "saldoDinheiro": 1500.00,
                                "quantidadeFichas": 15,
                                "mensagem": "Venda de fichas realizada com sucesso"
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "❌ Erro - Quantidade inválida ou fichas insuficientes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Fichas insuficientes",
                                "details": "Fichas atuais: 5 | Fichas solicitadas: 10",
                                "timestamp": "2025-07-08T11:10:00"
                            }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "❌ Erro - Carteira não encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                            {
                                "message": "Carteira não encontrada",
                                "details": "Nenhuma carteira com ID 999 foi localizada",
                                "timestamp": "2025-07-08T11:15:00"
                            }
                            """)
                    )
            )
    })
    ResponseEntity<SaldoDTO> venderFichas(
            @PathVariable("idCarteira") Integer id,
            @Parameter(description = "Quantidade de fichas a vender", example = "10")
            @RequestParam int quantidade
    ) throws RegraDeNegocioException;
}
