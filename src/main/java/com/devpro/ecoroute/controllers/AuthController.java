package com.devpro.ecoroute.controllers;

import com.devpro.ecoroute.dtos.auth.AuthRequestDTO;
import com.devpro.ecoroute.dtos.auth.AuthResponseDTO;
import com.devpro.ecoroute.models.User;
import com.devpro.ecoroute.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (User) auth.getPrincipal();

        // Gera o token de acesso
        var tokenData = tokenService.generateTokenData(user);

        // TODO: (Gera o refresh token (lógica a ser criada))
        // String refreshToken = "gerar_refresh_token_aqui"; // Placeholder

        // Converte o Instant para timestamp em milissegundos
        long expirationTimeMillis = tokenData.expiresAt().toEpochMilli();

        var response = new AuthResponseDTO(
                tokenData.token(),
                expirationTimeMillis
        );

        return ResponseEntity.ok(response);
    }
}