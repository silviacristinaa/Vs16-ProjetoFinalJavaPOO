package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.documentacao.PartidaControllerDoc;
import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.PartidaService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/partida")
public class PartidaController implements PartidaControllerDoc {

    private final PartidaService partidaService;

    @GetMapping
    public ResponseEntity<List<PartidaResponseDTO>> listar() throws RegraDeNegocioException {
        log.info("Listando todas as partidas...");
        List<PartidaResponseDTO> partidas = partidaService.listar();
        return new ResponseEntity<>(partidas, HttpStatus.OK);
    }

    // Endpoint de paginação
    @GetMapping("/paginado")
    public ResponseEntity<Page<PartidaResponseDTO>> listarPaginado(
            @Parameter(description = "Número da página que deseja visualizar", example = "0")
            @RequestParam(defaultValue = "0") int pagina,

            @Parameter(description = "Quantidade de registros por página", example = "10")
            @RequestParam(defaultValue = "10") int tamanhoPagina
    ) throws RegraDeNegocioException {
        log.info("Listando partidas paginadas...");
        Page<PartidaResponseDTO> partidas = partidaService.listarPaginado(pagina, tamanhoPagina);
        return ResponseEntity.ok(partidas);
    }

    @GetMapping("/{idPartida}")
    public ResponseEntity<PartidaResponseDTO> buscarPorId(@PathVariable("idPartida") Integer id) throws Exception {
        log.info("Buscando partida com ID: {}", id);
        PartidaResponseDTO partida = partidaService.buscarPartidaPorId(id);
        return new ResponseEntity<>(partida, HttpStatus.OK);
    }

    @DeleteMapping("/{idPartida}")
    public ResponseEntity<Void> deletar(@PathVariable("idPartida") Integer id) throws Exception {
        log.info("Deletando partida com ID: {}", id);
        partidaService.removerPartida(id);
        log.info("Partida removida com sucesso!");
        return ResponseEntity.ok().build();
    }

    //  Novo endpoint para criar partidas manualmente (para testes)
//    @PostMapping
//    public ResponseEntity<PartidaResponseDTO> criar(@RequestBody PartidaRequestDTO dto) throws RegraDeNegocioException {
//        log.info("Criando nova partida...");
//        PartidaResponseDTO response = partidaService.adicionarPartida(dto);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

}
