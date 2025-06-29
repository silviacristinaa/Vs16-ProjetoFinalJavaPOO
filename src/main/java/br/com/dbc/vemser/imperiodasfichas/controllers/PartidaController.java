package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.documentacao.PartidaControllerDoc;
import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.PartidaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/{idPartida}")
    public ResponseEntity<PartidaResponseDTO> buscarPorId(@PathVariable("idPartida") Integer id) throws Exception {
        log.info("Buscando partida com ID: {}", id);
        PartidaResponseDTO partida = partidaService.buscarPartidaPorId(id);
        return new ResponseEntity<>(partida, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PartidaResponseDTO> criar(@RequestBody @Valid PartidaRequestDTO partida) throws Exception {
        PartidaResponseDTO novaPartida = partidaService.adicionarPartida(partida);
        return new ResponseEntity<>(novaPartida, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idPartida}")
    public ResponseEntity<Void> deletar(@PathVariable("idPartida") Integer id) throws Exception {
        log.info("Deletando partida com ID: {}", id);
        partidaService.removerPartida(id);
        log.info("Partida removida com sucesso!");
        return ResponseEntity.ok().build();
    }
}
