package br.com.dbc.vemser.imperiodasfichas.security;

import br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao.LoginDTO;
import br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao.RegisterDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.UsuarioEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final UsuarioService usuarioService;

    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuarioEntityOptional = usuarioService.findByLogin(username);
        return usuarioEntityOptional
                .orElseThrow(() -> new UsernameNotFoundException("Usuario inválido!"));
    }

    public LoginDTO register(@Valid RegisterDTO usuarioCreateDTO) throws RegraDeNegocioException {
        if (usuarioService.findByLogin(usuarioCreateDTO.getLogin()).isPresent()) {
            throw new RuntimeException("Usuário já existe!");
        }
        if (!usuarioCreateDTO.getSenha().equals(usuarioCreateDTO.getConfirmarSenha())) {
            throw new RuntimeException("As senhas não conferem!");

        }
        UsuarioEntity usuario = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        usuario.setSenha(passwordEncoder.encode(usuarioCreateDTO.getSenha()));

        return objectMapper.convertValue(usuarioService.cadastrarUsuario(usuario), LoginDTO.class);

    }

}
