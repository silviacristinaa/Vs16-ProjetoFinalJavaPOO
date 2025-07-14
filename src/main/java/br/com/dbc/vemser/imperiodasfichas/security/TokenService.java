package br.com.dbc.vemser.imperiodasfichas.security;

import br.com.dbc.vemser.imperiodasfichas.entities.UsuarioEntity;
import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.imperiodasfichas.services.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TokenService {
    private static final String TOKEN_PREFIX = "Bearer";

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    private final UsuarioService usuarioService;

    public String generateToken(UsuarioEntity usuario) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.parseLong(expiration));

        // Adicionando os cargos ao token (única mudança necessária)
        List<String> cargos = usuario.getCargos().stream()
                .map(cargo -> cargo.getAuthority()) // getAuthority() retorna "ROLE_ADMIN", etc.
                .collect(Collectors.toList());

        return TOKEN_PREFIX + " " +
                Jwts.builder()
                        .setIssuer("Imperio das Fichas")
                        .claim(Claims.ID, usuario.getIdUsuario().toString())
                        .claim("cargos", cargos)
                        .setIssuedAt(now)
                        .setExpiration(exp)
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
    }

    public UsernamePasswordAuthenticationToken isValid(String token) throws RegraDeNegocioException {
        if (token != null) {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String userId = body.get(Claims.ID, String.class);

            // Obtendo os cargos do token
            List<String> cargos = body.get("cargos", List.class);

            if (userId != null) {
                UsuarioEntity usuario = usuarioService.findById(Integer.parseInt(userId));
                // Convertendo cargos para autoridades
                if (usuario != null && usuario.isEnabled()) {
                    List<SimpleGrantedAuthority> authorities = cargos.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    return new UsernamePasswordAuthenticationToken(
                            userId, null,  authorities // Agora com os cargos/cargos
                    );
                }
            }
        }
        return null;
    }

}
