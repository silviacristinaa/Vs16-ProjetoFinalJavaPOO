package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.JogadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogador")
public class JogadorController {

    private final JogadorService jogadorService;

    public JogadorController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }

    @GetMapping
    public List<JogadorEntity> listar() throws RegraDeNegocioException {
        return jogadorService.listar();
    }

    @GetMapping("{idJogador}")
    public JogadorEntity buscarPorId(@PathVariable("idJogador") Integer id) throws Exception {
        return jogadorService.buscarJogadorPorId(id);
    }

    @PostMapping
    public JogadorEntity criar(@RequestBody JogadorEntity jogador) throws RegraDeNegocioException {
        return jogadorService.adicionarJogador(jogador);
    }

    @PutMapping("/{idJogador}")
    public JogadorEntity atualizar(@PathVariable("idJogador") Integer id,
                                   @RequestBody JogadorEntity jogadorAtualizar) throws Exception {
        return jogadorService.atualizarJogador(id, jogadorAtualizar);
    }

    @DeleteMapping("/{idJogador}")
    public void deletar(@PathVariable("idJogador") Integer id) throws Exception {
        jogadorService.removerJogador(id);
    }
}
