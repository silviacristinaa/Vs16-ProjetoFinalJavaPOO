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

    @PutMapping("/{idCarteira}")
    public ResponseEntity<CarteiraEntity> atualizar(@PathVariable("idCarteira") Integer id,
                                                    @RequestBody CarteiraEntity carteiraAtualizar) throws RegraDeNegocioException {
        return new ResponseEntity<>(carteiraService.atualizarCarteira(id, carteiraAtualizar), HttpStatus.OK);
    }

    @PutMapping("/{idCarteira}/depositar") // /carteira/1/depositar?valor=50
    public ResponseEntity<CarteiraEntity> depositarDinheiro(@PathVariable("idCarteira") Integer id,
                                            @RequestParam double valor) throws RegraDeNegocioException {
        return new ResponseEntity<>(carteiraService.depositarDinheiro(id, valor), HttpStatus.OK);
    }

    @PutMapping("/{idCarteira}/sacar") // /carteira/1/sacar?valor=50
    public ResponseEntity<CarteiraEntity> sacarDinheiro(@PathVariable("idCarteira") Integer id,
                                        @RequestParam double valor) throws RegraDeNegocioException {
        return new ResponseEntity<>(carteiraService.sacarDinheiro(id, valor), HttpStatus.OK);
    }
}