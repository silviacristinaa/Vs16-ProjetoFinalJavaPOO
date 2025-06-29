package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.documentacao.JogoControllerDoc;
import br.com.dbc.vemser.imperiodasfichas.dtos.JogoRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.JogoResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.JogoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/jogo")
@Validated
@RequiredArgsConstructor
@Slf4j
public class JogoController implements JogoControllerDoc {
    private final JogoService jogoService;

    @GetMapping // GET localhost:8080/jogo
    public ResponseEntity<List<JogoResponseDTO>> list() throws RegraDeNegocioException {
        log.info("Listando todos os jogos...");
        List<JogoResponseDTO> jogos = jogoService.list();
        return new ResponseEntity<>(jogos, HttpStatus.OK);
    }

    @GetMapping("/{id}") // GET localhost:8080/jogo/{id}
    public ResponseEntity<JogoResponseDTO> findById(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        log.info("Buscando jogo com ID: {}", id);
        JogoResponseDTO jogo = jogoService.findById(id);
        return new ResponseEntity<>(jogo, HttpStatus.OK);
    }

    @PostMapping // POST localhost:8080/jogo
    public ResponseEntity<JogoResponseDTO> create(@Valid @RequestBody JogoRequestDTO jogo) throws RegraDeNegocioException {
        log.info("Criando novo jogo: {}", jogo);
        JogoResponseDTO novoJogo = jogoService.create(jogo);
        log.info("Jogo criado com sucesso: {}", novoJogo);
        return new ResponseEntity<>(novoJogo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // PUT localhost:8080/jogo/{id}
    public ResponseEntity<JogoResponseDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody JogoRequestDTO jogoAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando jogo com ID: {}", id);
        JogoResponseDTO jogoAtualizado = jogoService.update(id, jogoAtualizar);
        log.info("Jogo atualizado com sucesso: {}", jogoAtualizado);
        return new ResponseEntity<>(jogoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // DELETE localhost:8080/jogo/{id}
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        log.info("Deletando jogo com ID: {}", id);
        jogoService.delete(id);
        log.info("Jogo deletado com sucesso!");
        return ResponseEntity.ok().build();
    }
}
