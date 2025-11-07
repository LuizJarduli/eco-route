package com.devpro.ecoroute.controllers;

import com.devpro.ecoroute.dtos.base.PagedResponseDTO;
import com.devpro.ecoroute.dtos.user.UserResponseDTO;
import com.devpro.ecoroute.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Endpoints para consultar usuários")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser() {
        final UserResponseDTO user = userService.getLoggedUser();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        final UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Endpoint para listar todos os usuários com paginação.
     */
    @GetMapping
    public ResponseEntity<PagedResponseDTO<UserResponseDTO>> getAllUsers(
            // O Spring cria este objeto automaticamente a partir dos parâmetros da URL
            // @PageableDefault define os valores padrão se nenhum for enviado
            @ParameterObject @PageableDefault(size = 10, page = 0, sort = "name") Pageable pageable
    ) {
        PagedResponseDTO<UserResponseDTO> response = userService.getAllUsers(pageable);
        return ResponseEntity.ok(response);
    }

    // Endpoint para gerar um hash BCrypt de uma senha
    @Deprecated
    @GetMapping("/hash/{password}")
    public ResponseEntity<String> getHash(@PathVariable String password) {
        String hashedPassword = passwordEncoder.encode(password);
        return ResponseEntity.ok(hashedPassword);
    }
}
