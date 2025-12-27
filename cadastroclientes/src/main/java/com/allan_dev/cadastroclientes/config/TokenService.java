package com.allan_dev.cadastroclientes.config;

import com.allan_dev.cadastroclientes.entity.Funcionario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Essa é a senha secreta da balada. Só o segurança conhece.
    // Se alguém tentar falsificar a pulseira sem esse segredo, o segurança sabe.
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Funcionario funcionario) {
        try {
            // 1. Escolhemos o algoritmo de criptografia (o carimbo)
            var algoritmo = Algorithm.HMAC256(secret);

            // 2. Criamos a pulseira
            return JWT.create()
                    .withIssuer("API Cadastro Clientes") // Quem emitiu a pulseira? (Nome da sua empresa)
                    .withSubject(funcionario.getEmail()) // Quem é o dono da pulseira? (O email dele)
                    .withExpiresAt(dataExpiracao()) // Até quando vale? (Validade da pulseira)
                    .sign(algoritmo); // Carimba com o selo oficial!
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    // Método auxiliar para dizer que o token vence em 2 horas
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    // Método para LER a pulseira e devolver o dono (Subject)
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret); // Pega a mesma senha secreta
            return JWT.require(algoritmo)
                    .withIssuer("API Cadastro Clientes") // Verifica se foi a gente que emitiu
                    .build()
                    .verify(tokenJWT) // <--- A Mágica: verifica se o token é válido!
                    .getSubject(); // Devolve o dono (ex: "allan@email.com")
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
}
