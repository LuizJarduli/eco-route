package com.devpro.ecoroute.repositories;

import com.devpro.ecoroute.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a user by their unique username.
     * Spring Data JPA automatically implements this method.
     * @param username The username to search for.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findByUsername(String username);
}