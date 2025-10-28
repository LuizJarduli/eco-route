package com.devpro.ecoroute.services;

import com.devpro.ecoroute.dtos.base.PagedResponseDTO;
import com.devpro.ecoroute.dtos.user.UserResponseDTO;
import com.devpro.ecoroute.exceptions.ResourceNotFoundException;
import com.devpro.ecoroute.models.User;
import com.devpro.ecoroute.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * @summary Busca os dados do usuário requisitado
     *
     * @param id identificador do usuário
     *
     * @return UserResponseDTO
     *
     * @throws ResourceNotFoundException
     */
    public UserResponseDTO getUserById(UUID id) throws ResourceNotFoundException {
        final User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return UserResponseDTO.fromUser(user);
    }

    /**
     * @summary - Busca todos os usuários de forma paginada.
     *
     * @return - Lista paginada com os usuários encontrados na base
     */
    public PagedResponseDTO<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        Page<UserResponseDTO> dtoPage = userPage.map(UserResponseDTO::fromUser);
        return new PagedResponseDTO<>(dtoPage);
    }
}
