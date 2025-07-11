package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.LoginDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.UsuarioEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha) {
        return usuarioRepository.findByLoginAndSenha(login, senha);
    }

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public void cadastrarUsuario(LoginDTO loginDTO) throws RegraDeNegocioException {
        if (findByLogin(loginDTO.getLogin()).isPresent()) {
            throw new RegraDeNegocioException("Este login não está disponível. Por favor, escolha outro.");
        }

        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        loginDTO.setSenha(standardPasswordEncoder.encode(loginDTO.getSenha()));

        UsuarioEntity usuario = objectMapper.convertValue(loginDTO, UsuarioEntity.class);

        usuarioRepository.save(usuario);
    }
}
