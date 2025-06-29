package br.com.dbc.vemser.imperiodasfichas.documentacao;

import br.com.dbc.vemser.imperiodasfichas.dtos.CarteiraResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface CarteiraControllerDoc {
    @Operation(summary = "Listar contatos", description = "Lista todos os contatos cadastrados no sistema.")
    @ApiResponses (
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de contatos."),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção no servidor.")
            }
    )
    @GetMapping
    ResponseEntity<List<CarteiraResponseDTO>> listar() throws RegraDeNegocioException;
}
