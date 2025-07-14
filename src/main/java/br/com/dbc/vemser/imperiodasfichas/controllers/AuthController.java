package br.com.dbc.vemser.imperiodasfichas.controllers;

import br.com.dbc.vemser.imperiodasfichas.documentacao.AuthControllerDoc;
import br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao.LoginDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao.RegisterDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao.TrocarSenhaDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao.UsuarioResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.UsuarioEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.security.AuthenticationService;
import br.com.dbc.vemser.imperiodasfichas.security.TokenService;
import br.com.dbc.vemser.imperiodasfichas.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {
    public final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;
    private final UsuarioService usuarioService;

    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getSenha()
                );
        Authentication authentication =
                authenticationManager.authenticate(
                        usernamePasswordAuthenticationToken);

        UsuarioEntity usuarioValidado = (UsuarioEntity) authentication.getPrincipal();

        return tokenService.generateToken(usuarioValidado);
    }

    //TODO CRIAR USUARIO
    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody @Valid RegisterDTO registerDTO) throws RegraDeNegocioException {
        log.info("Registrando usuário: {}", registerDTO.getLogin());

        authenticationService.register(registerDTO);
        log.info("Usuário registrado. Tentando autenticar...");

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            registerDTO.getLogin(),
                            registerDTO.getSenha()
                    )
            );
            log.info("Autenticação OK. Gerando token...");

            String token = tokenService.generateToken((UsuarioEntity) auth.getPrincipal());
            log.info("Token gerado com sucesso");

            return ResponseEntity.ok(token);
        } catch (Exception e) {
            log.error("Falha na autenticação: {}", e.getMessage(), e);
            throw new RegraDeNegocioException("Falha ao autenticar usuário");
        }
    }

    @PostMapping("/desativar/{login}")
    public ResponseEntity<String> desativarUsuario(@PathVariable String login) throws RegraDeNegocioException {
        usuarioService.desativarUsuario(login);
        return ResponseEntity.ok("Usuário desativado com sucesso");
    }

    @PostMapping("/ativar/{login}")
    public ResponseEntity<String> ativarUsuario(@PathVariable String login) throws RegraDeNegocioException {
        usuarioService.ativarUsuario(login);
        return ResponseEntity.ok("Usuário ativado com sucesso");
    }

    @PatchMapping("/trocar-senha")
    public ResponseEntity<String> trocarSenha(@RequestBody @Valid TrocarSenhaDTO trocarSenhaDTO) throws RegraDeNegocioException {
        authenticationService.trocarSenha(trocarSenhaDTO);
        return ResponseEntity.ok("Senha atualizada com sucesso.");
    }

    @GetMapping("/logged")
    public ResponseEntity<UsuarioResponseDTO> getLoggedUser() throws RegraDeNegocioException {
        UsuarioResponseDTO usuarioLogado = usuarioService.getLoggedUser();
        return ResponseEntity.ok(usuarioLogado);
    }

}