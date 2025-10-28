package com.devpro.ecoroute.services;

import com.devpro.ecoroute.dtos.user.UserResponseDTO;
import com.devpro.ecoroute.exceptions.ResourceNotFoundException;
import com.devpro.ecoroute.models.User;
import com.devpro.ecoroute.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @summary - Busca os dados do usuário atualmente autenticado no contexto de segurança.
     *
     * @return - UserResponseDTO
     */
    public UserResponseDTO getLoggedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = (User) authentication.getPrincipal();
        return UserResponseDTO.fromUser(user);
    }

    public UserResponseDTO getUserById(UUID id) throws ResourceNotFoundException {
        final User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return UserResponseDTO.fromUser(user);
    }
}
