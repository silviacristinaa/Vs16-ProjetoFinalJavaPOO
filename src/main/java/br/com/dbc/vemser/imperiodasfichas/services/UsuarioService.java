package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.dtos.autenticacao.UsuarioResponseDTO;
import br.com.dbc.vemser.imperiodasfichas.entities.CargoEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.UsuarioEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public UsuarioEntity cadastrarUsuario(UsuarioEntity usuarioEntity) throws RegraDeNegocioException {
        if (findByLogin(usuarioEntity.getLogin()).isPresent()) {
            throw new RegraDeNegocioException("Este login não está disponível. Por favor, escolha outro.");
        }
        return usuarioRepository.save(usuarioEntity);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
    /* O UserDetailsService é uma interface central do Spring Security que você precisa implementar
     para carregar os detalhes do usuário durante a autenticação.
      .*/

    public UsuarioEntity create(UsuarioEntity usuario) {
        return usuarioRepository.save(usuario);
    }

    public void desativarUsuario(String login) throws RegraDeNegocioException {
        Integer idUsuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"))
                .getIdUsuario();

        UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));

        if (usuario.isEnabled()) {
            usuario.setAtivo("N");
        } else {
            throw new RegraDeNegocioException("Usuário já está desativado");
        }

        usuarioRepository.save(usuario);
    }

    public void ativarUsuario(String login) throws RegraDeNegocioException {
        Integer idUsuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"))
                .getIdUsuario();

        UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));

        if (!usuario.isEnabled()) {
            usuario.setAtivo("S");
        } else {
            throw new RegraDeNegocioException("Usuário já está ativo");
        }

        usuarioRepository.save(usuario);
    }

    public void save(UsuarioEntity usuario) throws RegraDeNegocioException {
        usuarioRepository.save(usuario);
    }

    public Integer getIdLoggedUser() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Integer.valueOf(principal);
    }

    public UsuarioResponseDTO getLoggedUser() throws RegraDeNegocioException {
        UsuarioEntity usuarioLogado = findById(getIdLoggedUser());

        UsuarioResponseDTO dto = objectMapper.convertValue(usuarioLogado, UsuarioResponseDTO.class);
        // Mapeia os nomes dos cargos para um Set<String>
        Set<String> nomesCargos = usuarioLogado.getCargos().stream()
                .map(CargoEntity::getNome)
                .collect(Collectors.toSet());

        dto.setNomeCargo(nomesCargos);

        return dto;
    }

    public UsuarioEntity findById(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário com ID " + idUsuario + " não encontrado."));
    }

    public UsuarioEntity findByLoggedUser() throws RegraDeNegocioException {
        Integer idLoggedUser = getIdLoggedUser();
        return usuarioRepository.findById(idLoggedUser)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário com ID " + idLoggedUser + " não encontrado."));
    }
}
