package br.com.dbc.vemser.imperiodasfichas.documentacao;

import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.CarteiraRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.CarteiraResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.carteira.SaldoDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface CarteiraControllerDoc {
    @Operation(summary = "Listar carteiras", description = "Lista todas as carteiras cadastradas no sistema.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de carteiras."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    ResponseEntity<List<CarteiraResponseDTO>> listar() throws RegraDeNegocioException;

    @Operation(summary = "Buscar carteira por ID", description = "Busca uma carteira específica pelo seu ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a carteira encontrada."),
                    @ApiResponse(responseCode = "404", description = "Carteira não encontrada."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    ResponseEntity<CarteiraResponseDTO> buscarPorId(@PathVariable("idCarteira") Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Buscar carteira pelo ID do jogador", description = "Busca a carteira de um jogador específico.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a carteira do jogador."),
                    @ApiResponse(responseCode = "404", description = "Carteira ou jogador não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    ResponseEntity<CarteiraResponseDTO> buscarPorIdJogador(@PathVariable("idJogador") Integer idJogador) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar carteira", description = "Atualiza os dados de fichas e dinheiro de uma carteira.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a carteira atualizada."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados enviados."),
                    @ApiResponse(responseCode = "404", description = "Carteira não encontrada."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    ResponseEntity<CarteiraResponseDTO> atualizar(@PathVariable("idCarteira") Integer id,
                                                  @RequestBody @Valid CarteiraRequestDTO carteiraAtualizarDTO) throws RegraDeNegocioException;

    @Operation(summary = "Depositar dinheiro", description = "Adiciona um valor ao saldo de dinheiro da carteira.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a carteira com o novo saldo."),
                    @ApiResponse(responseCode = "400", description = "Valor de depósito inválido."),
                    @ApiResponse(responseCode = "404", description = "Carteira não encontrada."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    ResponseEntity<SaldoDTO> depositarDinheiro(@PathVariable("idCarteira") Integer id,
                                                          @RequestParam double valor) throws RegraDeNegocioException;

    @Operation(summary = "Sacar dinheiro", description = "Retira um valor do saldo de dinheiro da carteira.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a carteira com o novo saldo."),
                    @ApiResponse(responseCode = "400", description = "Valor de saque inválido ou saldo insuficiente."),
                    @ApiResponse(responseCode = "404", description = "Carteira não encontrada."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    ResponseEntity<SaldoDTO> sacarDinheiro(@PathVariable("idCarteira") Integer id,
                                                      @RequestParam double valor) throws RegraDeNegocioException;

    @Operation(summary = "Comprar fichas", description = "Usa o saldo de dinheiro para comprar fichas.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a carteira com o saldo de fichas e dinheiro atualizados."),
                    @ApiResponse(responseCode = "400", description = "Quantidade de fichas inválida ou dinheiro insuficiente."),
                    @ApiResponse(responseCode = "404", description = "Carteira não encontrada."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    ResponseEntity<SaldoDTO> comprarFichas(@PathVariable("idCarteira") Integer id,
                                                      @RequestParam int quantidade) throws RegraDeNegocioException;

    @Operation(summary = "Vender fichas", description = "Converte fichas em dinheiro, atualizando o saldo da carteira.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a carteira com o saldo de fichas e dinheiro atualizados."),
                    @ApiResponse(responseCode = "400", description = "Quantidade de fichas inválida ou fichas insuficientes."),
                    @ApiResponse(responseCode = "404", description = "Carteira não encontrada."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    ResponseEntity<SaldoDTO> venderFichas(@PathVariable("idCarteira") Integer id,
                                          @RequestParam int quantidade) throws RegraDeNegocioException;
}