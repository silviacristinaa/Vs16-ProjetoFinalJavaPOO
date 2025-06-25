package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.CarteiraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {

    private final CarteiraService carteiraService;

    public CarteiraController(CarteiraService carteiraService) {
        this.carteiraService = carteiraService;
    }

    @GetMapping
    public ResponseEntity<List<CarteiraEntity>> listar() throws RegraDeNegocioException {
        return new ResponseEntity<>(carteiraService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{idCarteira}")
    public ResponseEntity<CarteiraEntity> buscarPorId(@PathVariable("idCarteira") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(carteiraService.buscarCarteiraPorId(id), HttpStatus.OK);
    }

    @GetMapping("/jogador/{idJogador}")
    public ResponseEntity<CarteiraEntity> buscarPorIdJogador(@PathVariable("idJogador") Integer idJogador) throws RegraDeNegocioException {
        return new ResponseEntity<>(carteiraService.buscarCarteiraPorIdJogador(idJogador), HttpStatus.OK);
    }
}