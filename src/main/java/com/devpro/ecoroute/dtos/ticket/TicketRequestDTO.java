package com.devpro.ecoroute.dtos.ticket;

import com.devpro.ecoroute.models.enums.Priority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO (JSON) que a API recebe para criar um novo Ticket.
 */
public record TicketRequestDTO(
        @NotNull(message = "Title cannot be null")
        @Size(min = 5, max = 255, message = "Title must be between 5 and 255 characters")
        String title,

        String description,

        @NotNull(message = "Priority cannot be null")
        Priority priority,

        // Coordenadas (opcionais)
        BigDecimal latitude,
        BigDecimal longitude,

        // Endereço (opcional)
        String streetAddress,
        String neighborhood,
        String city,
        String state,
        String postalCode,

        @NotNull(message = "Waste Type ID cannot be null")
        Long wasteTypeId,

        @NotEmpty(message = "At least one photo URL is required")
        List<String> photoUrls
) {}