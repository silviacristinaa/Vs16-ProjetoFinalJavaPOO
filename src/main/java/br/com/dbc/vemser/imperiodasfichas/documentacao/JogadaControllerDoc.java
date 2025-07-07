package br.com.dbc.vemser.imperiodasfichas.documentacao;

import br.com.dbc.vemser.imperiodasfichas.dtos.jogada.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Jogada", description = "Operações relacionadas às jogadas dos jogadores em diferentes jogos.")
public interface JogadaControllerDoc {

    @Operation(summary = "Jogar Roleta Clássica", description = "Realiza uma jogada na Roleta Clássica, apostando em par ou ímpar.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Jogada realizada com sucesso. Retorna o resultado da partida."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados da aposta ou se há fichas suficientes."),
                    @ApiResponse(responseCode = "404", description = "Jogador não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    ResponseEntity<RoletaParImparResponseDTO> jogarRoletaClassica(@RequestBody @Valid RoletaParImparRequestDTO jogada) throws Exception;

    @Operation(summary = "Jogar Roleta das Cores", description = "Realiza uma jogada na Roleta das Cores.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Jogada realizada com sucesso. Retorna o resultado da partida."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados da aposta ou se há fichas suficientes."),
                    @ApiResponse(responseCode = "404", description = "Jogador não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    ResponseEntity<RoletaCoresResponseDTO> jogarRoletaCores(@RequestBody @Valid RoletaCoresRequestDTO jogada) throws Exception;

    @Operation(summary = "Jogar Caça-Níquel", description = "Realiza uma jogada no Caça-Níquel.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Jogada realizada com sucesso. Retorna o resultado da partida."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados da aposta ou se há fichas suficientes."),
                    @ApiResponse(responseCode = "404", description = "Jogador não encontrado."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    ResponseEntity<CacaNiquelResponseDTO> jogarCacaNiquel(@RequestBody @Valid CacaNiquelRequestDTO jogada) throws Exception;
}