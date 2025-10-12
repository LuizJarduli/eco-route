package com.devpro.ecoroute.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.devpro.ecoroute.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public record TokenData(String token, Instant expiresAt) {}

    /**
     * Gera um token JWT para o usuário fornecido.
     * @param user O usuário para o qual o token será gerado.
     * @return Uma string representando o token JWT.
     */
    public TokenData generateTokenData(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Instant expirationInstant = genExpirationDate();

            String token = JWT.create()
                    .withIssuer("eco-route-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(expirationInstant)
                    .sign(algorithm);

            return new TokenData(token, expirationInstant);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }

    /**
     * Valida um token JWT e retorna o login do usuário se o token for válido.
     * @param token O token JWT a ser validado.
     * @return O login (subject) do usuário se o token for válido, caso contrário, retorna uma string vazia.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("eco-route-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return ""; // Retorna vazio se o token for inválido
        }
    }

    /**
     * Calcula a data de expiração do token.
     * @return Um Instant representando a data/hora de expiração (ex: 24 horas a partir de agora).
     */
    private Instant genExpirationDate() {
        return LocalDateTime.now()
                .plusDays(1) // O token expira em 1 dia
                .toInstant(ZoneOffset.of("-03:00")); // Fuso horário de Brasília
    }
}