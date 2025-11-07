package com.devpro.ecoroute.dtos.ticket;

import com.devpro.ecoroute.models.Ticket;
import com.devpro.ecoroute.models.TicketPhoto;
import com.devpro.ecoroute.models.enums.Priority;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * DTO (JSON) que a API retorna após a criação do Ticket.
 */
public record TicketResponseDTO(
        UUID id,
        String title,
        String description,
        Priority priority,
        BigDecimal latitude,
        BigDecimal longitude,
        String streetAddress,
        String city,
        String state,
        String createdAt,

        // DTOs aninhados para os relacionamentos
        AuthorDTO author,
        WasteTypeDTO wasteType,
        List<String> photoUrls
) {
    public record AuthorDTO(UUID id, String name) {}

    public record WasteTypeDTO(Long id, String name) {}

    public static TicketResponseDTO fromEntity(Ticket ticket) {
        // Mapeia os dados do autor
        AuthorDTO authorDto = new AuthorDTO(
                ticket.getUser().getId(),
                ticket.getUser().getName()
        );

        // Mapeia os dados do tipo de resíduo
        WasteTypeDTO wasteTypeDto = new WasteTypeDTO(
                ticket.getWasteType().getId(),
                ticket.getWasteType().getName()
        );

        // Mapeia a lista de URLs das fotos
        List<String> urls = ticket.getPhotos().stream()
                .map(TicketPhoto::getImageUrl)
                .toList();

        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getPriority(),
                ticket.getLatitude(),
                ticket.getLongitude(),
                ticket.getStreetAddress(),
                ticket.getCity(),
                ticket.getState(),
                ticket.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                authorDto,
                wasteTypeDto,
                urls
        );
    }
}