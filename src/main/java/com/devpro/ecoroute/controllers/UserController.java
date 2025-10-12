package com.devpro.ecoroute.controllers;

import com.devpro.ecoroute.dtos.user.UserResponseDTO;
import com.devpro.ecoroute.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser(@AuthenticationPrincipal User user) {
        final var responseDto = new UserResponseDTO(user.getId().toString(),
                user.getUsername(),
                user.getName(),
                user.getProfilePic());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<String> getAllUsers() {
        // TODO: finalizar implementacao
        return ResponseEntity.ok("Lista de todos os usuários (protegido)");
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Endpoint para gerar um hash BCrypt de uma senha
    @GetMapping("/hash/{password}")
    public ResponseEntity<String> getHash(@PathVariable String password) {
        String hashedPassword = passwordEncoder.encode(password);
        return ResponseEntity.ok(hashedPassword);
    }
}
