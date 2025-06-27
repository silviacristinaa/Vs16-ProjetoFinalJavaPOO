package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.dtos.CarteiraRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.CarteiraResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.CarteiraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/carteira")
public class CarteiraController {

    private final CarteiraService carteiraService;

    @GetMapping
    public ResponseEntity<List<CarteiraResponseDTO>> listar() throws RegraDeNegocioException {
        log.info("Requisição para listar todas as carteiras recebida.");
        List<CarteiraResponseDTO> carteiras = carteiraService.listar();
        log.info("Lista de carteiras retornada com sucesso.");
        return new ResponseEntity<>(carteiras, HttpStatus.OK);
    }

    @GetMapping("/{idCarteira}")
    public ResponseEntity<CarteiraResponseDTO> buscarPorId(@PathVariable("idCarteira") Integer id) throws RegraDeNegocioException {
        log.info("Requisição para buscar carteira pelo ID: {}", id);
        CarteiraResponseDTO carteira = carteiraService.buscarCarteiraPorId(id);
        log.info("Carteira com ID {} encontrada e retornada com sucesso.", id);
        return new ResponseEntity<>(carteira, HttpStatus.OK);
    }

    @GetMapping("/jogador/{idJogador}")
    public ResponseEntity<CarteiraResponseDTO> buscarPorIdJogador(@PathVariable("idJogador") Integer idJogador) throws RegraDeNegocioException {
        log.info("Requisição para buscar carteira pelo ID do jogador: {}", idJogador);
        CarteiraResponseDTO carteira = carteiraService.buscarCarteiraPorIdJogador(idJogador);
        log.info("Carteira do jogador {} encontrada e retornada com sucesso.", idJogador);
        return new ResponseEntity<>(carteira, HttpStatus.OK);
    }


    @PutMapping("/{idCarteira}")
    public ResponseEntity<CarteiraResponseDTO> atualizar(@PathVariable("idCarteira") Integer id,
                                                         @RequestBody @Valid CarteiraRequestDTO carteiraAtualizarDTO) throws RegraDeNegocioException {
        log.info("Requisição para atualizar carteira com ID: {}", id);
        CarteiraResponseDTO carteiraAtualizada = carteiraService.atualizarCarteira(id, carteiraAtualizarDTO);
        log.info("Carteira com ID {} atualizada e retornada com sucesso.", id);
        return new ResponseEntity<>(carteiraAtualizada, HttpStatus.OK);
    }

    @PutMapping("/{idCarteira}/depositar")
    public ResponseEntity<CarteiraResponseDTO> depositarDinheiro(@PathVariable("idCarteira") Integer id,
                                                                 @RequestParam double valor) throws RegraDeNegocioException {
        log.info("Requisição para depositar R$ {} na carteira ID: {}", valor, id);
        CarteiraResponseDTO carteiraAtualizada = carteiraService.depositarDinheiro(id, valor);
        log.info("Depósito na carteira ID {} processado com sucesso.", id);
        return new ResponseEntity<>(carteiraAtualizada, HttpStatus.OK);
    }

    @PutMapping("/{idCarteira}/sacar")
    public ResponseEntity<CarteiraResponseDTO> sacarDinheiro(@PathVariable("idCarteira") Integer id,
                                                             @RequestParam double valor) throws RegraDeNegocioException {
        log.info("Requisição para sacar R$ {} da carteira ID: {}", valor, id);
        CarteiraResponseDTO carteiraAtualizada = carteiraService.sacarDinheiro(id, valor);
        log.info("Saque da carteira ID {} processado com sucesso.", id);
        return new ResponseEntity<>(carteiraAtualizada, HttpStatus.OK);
    }

    @PutMapping("/{idCarteira}/comprar")
    public ResponseEntity<CarteiraResponseDTO> comprarFichas(@PathVariable("idCarteira") Integer id,
                                                             @RequestParam int quantidade) throws RegraDeNegocioException {
        log.info("Requisição para comprar {} fichas para a carteira ID: {}", quantidade, id);
        CarteiraResponseDTO carteiraAtualizada = carteiraService.comprarFichas(id, quantidade);
        log.info("Compra de fichas para a carteira ID {} processada com sucesso.", id);
        return new ResponseEntity<>(carteiraAtualizada, HttpStatus.OK);
    }

    @PutMapping("/{idCarteira}/vender")
    public ResponseEntity<CarteiraResponseDTO> venderFichas(@PathVariable("idCarteira") Integer id,
                                                            @RequestParam int quantidade) throws RegraDeNegocioException {
        log.info("Requisição para vender {} fichas da carteira ID: {}", quantidade, id);
        CarteiraResponseDTO carteiraAtualizada = carteiraService.venderFichas(id, quantidade);
        log.info("Venda de fichas da carteira ID {} processada com sucesso.", id);
        return new ResponseEntity<>(carteiraAtualizada, HttpStatus.OK);
    }
}