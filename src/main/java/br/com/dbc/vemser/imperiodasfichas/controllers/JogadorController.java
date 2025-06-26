package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.JogadorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/jogador")
public class JogadorController {

    private final JogadorService jogadorService;

    @GetMapping
    public ResponseEntity<List<JogadorEntity>> listar() throws RegraDeNegocioException {
        return new ResponseEntity<>(jogadorService.listar(), HttpStatus.OK);
    }

    @GetMapping("{idJogador}")
    public ResponseEntity<JogadorEntity> buscarPorId(@PathVariable("idJogador") Integer id) throws Exception {
        return new ResponseEntity<>(jogadorService.buscarJogadorPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JogadorEntity> criar(@RequestBody JogadorEntity jogador) throws RegraDeNegocioException {
        log.info("Criando jogador na controller...");
        JogadorEntity j = jogadorService.adicionarJogador(jogador);
        log.info("Jogador criado!");
        return new ResponseEntity<>(j, HttpStatus.CREATED);
    }

    @PutMapping("/{idJogador}")
    public ResponseEntity<JogadorEntity> atualizar(@PathVariable("idJogador") Integer id,
                                   @RequestBody JogadorEntity jogadorAtualizar) throws Exception {
        log.info("Atualizando jogador na controller...");
        JogadorEntity j = jogadorService.atualizarJogador(id, jogadorAtualizar);
        log.info("Jogador atualizado!");
        return new ResponseEntity<>(j, HttpStatus.OK);
    }

    @DeleteMapping("/{idJogador}")
    public ResponseEntity<Void> deletar(@PathVariable("idJogador") Integer id) throws Exception {
        log.info("Removendo jogador na controller...");
        jogadorService.removerJogador(id);
        log.info("Jogador removido!");
        return ResponseEntity.ok().build();
    }
}
