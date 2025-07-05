package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.documentacao.JogadorControllerDoc;
import br.com.dbc.vemser.imperiodasfichas.dtos.jogador.*;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.JogadorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/jogador")
@RequiredArgsConstructor
public class JogadorController implements JogadorControllerDoc {

    private final JogadorService jogadorService;

    @GetMapping
    public ResponseEntity<List<JogadorResponseDTO>> list() throws RegraDeNegocioException {
        log.info("Listando todos os jogadores...");
        List<JogadorResponseDTO> jogadores = jogadorService.list();
        return new ResponseEntity<>(jogadores, HttpStatus.OK);
    }

    @GetMapping("/{idJogador}")
    public ResponseEntity<JogadorResponseDTO> findById(@PathVariable("idJogador") Integer idJogador) throws RegraDeNegocioException {
        log.info("Buscando jogador com ID: {}", idJogador);
        JogadorResponseDTO jogador = jogadorService.findById(idJogador);
        return new ResponseEntity<>(jogador, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JogadorResponseDTO> create(@Valid @RequestBody JogadorRequestDTO jogador) throws RegraDeNegocioException {
        log.info("Criando novo jogador: {}", jogador);
        JogadorResponseDTO novoJogador = jogadorService.create(jogador);
        log.info("Jogador criado com sucesso: {}", novoJogador);
        return new ResponseEntity<>(novoJogador, HttpStatus.CREATED);
    }

    @PutMapping("/{idJogador}")
    public ResponseEntity<JogadorResponseDTO> update(
            @PathVariable("idJogador") Integer idJogador,
            @Valid @RequestBody JogadorRequestDTO jogadorAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando jogador com ID: {}", idJogador);
        JogadorResponseDTO jogadorAtualizado = jogadorService.update(idJogador, jogadorAtualizar);
        log.info("Jogador atualizado com sucesso: {}", jogadorAtualizado);
        return new ResponseEntity<>(jogadorAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{idJogador}")
    public ResponseEntity<Void> delete(@PathVariable("idJogador") Integer idJogador) throws RegraDeNegocioException {
        log.info("Removendo jogador com ID: {}", idJogador);
        jogadorService.delete(idJogador);
        log.info("Jogador removido com sucesso!");
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/ranking")
//    public ResponseEntity<List<JogadorRankingDTO>> getRanking() throws RegraDeNegocioException {
//        log.info("Buscando ranking de jogadores...");
//        List<JogadorRankingDTO> ranking = jogadorService.getRanking();
//        return new ResponseEntity<>(ranking, HttpStatus.OK);
//    }
}