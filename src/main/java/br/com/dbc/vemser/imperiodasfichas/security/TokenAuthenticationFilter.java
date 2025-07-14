package br.com.dbc.vemser.imperiodasfichas.security;

import br.com.dbc.vemser.imperiodasfichas.exceptions.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenFromHeader = getTokenFromHeader(request);

        UsernamePasswordAuthenticationToken user = null;
        try {
            user = tokenService.isValid(tokenFromHeader);
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
        SecurityContextHolder.getContext().setAuthentication(user);
        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith(BEARER)) {
            return null;
        }
        return token.replace(BEARER, "");
    }
}