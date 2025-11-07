package com.devpro.ecoroute.services;

import com.devpro.ecoroute.dtos.base.PagedResponseDTO;
import com.devpro.ecoroute.dtos.ticket.HeatmapPointDTO;
import com.devpro.ecoroute.dtos.ticket.TicketRequestDTO;
import com.devpro.ecoroute.dtos.ticket.TicketResponseDTO;
import com.devpro.ecoroute.dtos.user.UserResponseDTO;
import com.devpro.ecoroute.exceptions.ResourceNotFoundException;
import com.devpro.ecoroute.models.Ticket;
import com.devpro.ecoroute.models.TicketPhoto;
import com.devpro.ecoroute.models.User;
import com.devpro.ecoroute.models.WasteType;
import com.devpro.ecoroute.models.enums.Priority;
import com.devpro.ecoroute.repositories.TicketRepository;
import com.devpro.ecoroute.repositories.WasteTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante!

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final WasteTypeRepository wasteTypeRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, WasteTypeRepository wasteTypeRepository) {
        this.ticketRepository = ticketRepository;
        this.wasteTypeRepository = wasteTypeRepository;
    }

    /**
     * Cria um novo Ticket no sistema.
     * Este método é transacional.
     */
    @Transactional
    public TicketResponseDTO createTicket(TicketRequestDTO dto) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        WasteType wasteType = wasteTypeRepository.findById(dto.wasteTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("WasteType not found with id: " + dto.wasteTypeId()));

        Ticket newTicket = new Ticket();
        newTicket.setUser(currentUser);
        newTicket.setWasteType(wasteType);
        newTicket.setTitle(dto.title());
        newTicket.setDescription(dto.description());
        newTicket.setPriority(dto.priority());
        newTicket.setLatitude(dto.latitude());
        newTicket.setLongitude(dto.longitude());
        newTicket.setStreetAddress(dto.streetAddress());
        newTicket.setNeighborhood(dto.neighborhood());
        newTicket.setCity(dto.city());
        newTicket.setState(dto.state());
        newTicket.setPostalCode(dto.postalCode());

        List<TicketPhoto> photos = dto.photoUrls().stream()
                .map(url -> {
                    TicketPhoto photo = new TicketPhoto();
                    photo.setImageUrl(url);
                    photo.setTicket(newTicket);
                    return photo;
                })
                .collect(Collectors.toList());

        newTicket.setPhotos(photos);

        Ticket savedTicket = ticketRepository.save(newTicket);

        return TicketResponseDTO.fromEntity(savedTicket);
    }

    /**
     * @summary - Busca todos os usuários de forma paginada.
     *
     * @param pageable Parametros de busca da listagem
     *
     * @return - Lista paginada com os chamados (tickets) encontrados na base
     */
    public PagedResponseDTO<TicketResponseDTO> getAllTickets(Pageable pageable) {
        Page<Ticket> ticketPage = ticketRepository.findAll(pageable);
        Page<TicketResponseDTO> dtoPage = ticketPage.map(TicketResponseDTO::fromEntity);
        return new PagedResponseDTO<>(dtoPage);
    }

    /**
     * summary - Busca uma lista de coordenadas organizando do maior para a maior prioridade
     *
     * @return Lista de <b>HeatmapPointDTO</b>
     */
    public List<HeatmapPointDTO> getHeatmapData() {
        List<Ticket> openTickets = ticketRepository.findAll();

        return openTickets.stream()
                // Filtra apenas os que têm coordenadas válidas
                .filter(ticket -> ticket.getLatitude() != null && ticket.getLongitude() != null)
                // Mapeia para o DTO
                .map(ticket -> new HeatmapPointDTO(
                        ticket.getLatitude(),
                        ticket.getLongitude(),
                        getPriorityWeight(ticket.getPriority()) // Converte Enum em peso
                ))
                .collect(Collectors.toList());
    }

    private int getPriorityWeight(Priority priority) {
        return switch (priority) {
            case HIGH -> 3;
            case MEDIUM -> 2;
            default -> 1;
        };
    }
}