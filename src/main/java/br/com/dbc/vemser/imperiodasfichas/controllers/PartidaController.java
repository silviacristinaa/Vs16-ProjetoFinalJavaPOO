package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaCreateDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.PartidaDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.PartidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/partida")
public class PartidaController {

    private final PartidaService partidaService;

    @GetMapping
    public ResponseEntity<List<PartidaDTO>> listar() throws RegraDeNegocioException {
        return new ResponseEntity<>(partidaService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{idPartida}")
    public ResponseEntity<PartidaDTO> buscarPorId(@PathVariable("idPartida") Integer id) throws Exception {
        return new ResponseEntity<>(partidaService.buscarPartidaPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PartidaDTO> criar(@RequestBody @Valid PartidaCreateDTO partida) throws Exception {
        PartidaDTO p = partidaService.adicionarPartida(partida);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idPartida}")
    public ResponseEntity<Void> deletar(@PathVariable("idPartida") Integer id) throws Exception {
        partidaService.removerPartida(id);
        return ResponseEntity.ok().build();
    }
}
