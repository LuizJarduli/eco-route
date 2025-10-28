package com.devpro.ecoroute.controllers;

import com.devpro.ecoroute.dtos.user.UserResponseDTO;
import com.devpro.ecoroute.models.User;
import com.devpro.ecoroute.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser() {
        final UserResponseDTO user = new UserService().getLoggedUser();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        final UserResponseDTO user = new UserService().getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<String> getAllUsers() {
        // TODO: finalizar implementacao
        return ResponseEntity.ok("Lista de todos os usuários (protegido)");
    }

    // Endpoint para gerar um hash BCrypt de uma senha
    @Deprecated
    @GetMapping("/hash/{password}")
    public ResponseEntity<String> getHash(@PathVariable String password) {
        String hashedPassword = passwordEncoder.encode(password);
        return ResponseEntity.ok(hashedPassword);
    }
}
