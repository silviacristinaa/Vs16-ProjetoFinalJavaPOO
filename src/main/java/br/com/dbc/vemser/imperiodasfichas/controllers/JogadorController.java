package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.dtos.JogadorRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.JogadorResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.JogadorService;
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
@RequestMapping("/jogador")
public class JogadorController {

    private final JogadorService jogadorService;

    @GetMapping
    public ResponseEntity<List<JogadorResponseDTO>> listar() throws RegraDeNegocioException {
        return new ResponseEntity<>(jogadorService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{idJogador}")
    public ResponseEntity<JogadorResponseDTO> buscarPorId(@PathVariable("idJogador") Integer id) throws Exception {
        return new ResponseEntity<>(jogadorService.buscarJogadorPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JogadorResponseDTO> criar(@RequestBody @Valid JogadorRequestDTO jogador) throws RegraDeNegocioException {
        log.info("Criando novo jogador: {}", jogador);
        JogadorResponseDTO novoJogador = jogadorService.adicionarJogador(jogador);
        log.info("Jogador criado com sucesso: {}", novoJogador);
        return new ResponseEntity<>(novoJogador, HttpStatus.CREATED);
    }

    @PutMapping("/{idJogador}")
    public ResponseEntity<JogadorResponseDTO> atualizar(@PathVariable("idJogador") Integer id,
                                                     @RequestBody @Valid JogadorRequestDTO jogadorAtualizar) throws Exception {
        log.info("Atualizando jogador com ID: {}", id);
        JogadorResponseDTO jogadorAtualizado = jogadorService.atualizarJogador(id, jogadorAtualizar);
        log.info("Jogador atualizado com sucesso: {}", jogadorAtualizado);
        return new ResponseEntity<>(jogadorAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{idJogador}")
    public ResponseEntity<Void> deletar(@PathVariable("idJogador") Integer id) throws Exception {
        log.info("Removendo jogador com ID: {}", id);
        jogadorService.removerJogador(id);
        log.info("Jogador removido com sucesso!");
        return ResponseEntity.ok().build();
    }
}
