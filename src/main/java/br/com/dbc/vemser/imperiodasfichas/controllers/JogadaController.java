//package br.com.dbc.vemser.imperiodasfichas.controllers;
//
//import br.com.dbc.vemser.imperiodasfichas.documentacao.JogadaControllerDoc;
//import br.com.dbc.vemser.imperiodasfichas.dtos.jogada.*;
//import br.com.dbc.vemser.imperiodasfichas.services.JogadaService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/jogada")
//public class JogadaController implements JogadaControllerDoc {
//
//    private final JogadaService jogadaService;
//
//    @PostMapping("/roleta/classica")
//    public ResponseEntity<RoletaParImparResponseDTO> jogarRoletaClassica(@RequestBody @Valid RoletaParImparRequestDTO jogada) throws Exception {
//        RoletaParImparResponseDTO response = jogadaService.jogarRoletaParImpar(jogada);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/roleta/cores")
//    public ResponseEntity<RoletaCoresResponseDTO> jogarRoletaCores(@RequestBody @Valid RoletaCoresRequestDTO jogada) throws Exception {
//        RoletaCoresResponseDTO response = jogadaService.jogarRoletaCores(jogada);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/cacaniquel")
//    public ResponseEntity<CacaNiquelResponseDTO> jogarCacaNiquel(@RequestBody @Valid CacaNiquelRequestDTO jogada) throws Exception {
//        CacaNiquelResponseDTO response = jogadaService.jogarCacaNiquel(jogada);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//}
