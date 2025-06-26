package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.entities.JogoEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.JogoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/jogo")
@Validated
public class JogoController {
    private final JogoService jogoService;

    public JogoController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    @GetMapping // GET localhost:8080/jogo
    public List<JogoEntity> listar() throws RegraDeNegocioException {
        return jogoService.list();
    }

    @GetMapping("/{id}") // GET localhost:8080/jogo/{id}
    public JogoEntity buscarPorId(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        return jogoService.findById(id);
    }

    @PostMapping // POST localhost:8080/jogo
    public JogoEntity criar(@Valid @RequestBody JogoEntity jogo) throws RegraDeNegocioException {
        return jogoService.create(jogo);
    }

    @PutMapping("/{id}") // PUT localhost:8080/jogo/{id}
    public JogoEntity atualizar(@PathVariable("id") Integer id, @Valid @RequestBody JogoEntity jogoAtualizar) throws RegraDeNegocioException {
        return jogoService.update(id, jogoAtualizar);
    }

    @DeleteMapping("/{id}") // DELETE localhost:8080/jogo/{id}
    public void deletar(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        jogoService.delete(id);
    }
}
