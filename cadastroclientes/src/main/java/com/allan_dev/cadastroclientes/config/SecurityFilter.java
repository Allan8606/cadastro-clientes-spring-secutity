package com.allan_dev.cadastroclientes.config;


import com.allan_dev.cadastroclientes.repository.FuncionarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Diz pro Spring: "Isso é um componente genérico, carregue ele"
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter { // Herda de "Filtro que roda uma vez por requisição"


    private final TokenService tokenService; // Ferramenta de ler pulseira

    private final FuncionarioRepository repository; // Para buscar o usuário no banco

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Pegar o token do cabeçalho (Recuperar a pulseira)
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            // 2. Validar o token (Luz negra)
            var subject = tokenService.getSubject(tokenJWT); // Pega o email (ex: allan@email.com)

            // 3. Encontrar a pessoa no banco (Bater o crachá)
            var usuario = repository.findByEmail(subject).get(); // Acha o funcionário real

            // 4. Mágica do Spring: Avisar o sistema que essa pessoa está logada!
            // Criamos um objeto de autenticação forçada
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            // Colocamos ele dentro da "Sala de Segurança" (SecurityContext)
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 5. Segue o baile! (Continua para o próximo passo)
        filterChain.doFilter(request, response);
    }

    // Método auxiliar só para tirar o texto "Bearer " que vem antes do token
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}