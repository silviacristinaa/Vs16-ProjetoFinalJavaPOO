package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.dtos.JogadaResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.RoletaParImparRequestDTO;
import br.com.dbc.vemser.imperiodasfichas.services.JogadaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/jogada")
public class JogadaController {

    private final JogadaService jogadaService;

    @PostMapping("/roleta")
    public ResponseEntity<JogadaResponseDTO> jogarRoletaParImpar(@RequestBody @Valid RoletaParImparRequestDTO jogada) throws Exception {
        JogadaResponseDTO response = jogadaService.jogarRoletaParImpar(jogada);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
