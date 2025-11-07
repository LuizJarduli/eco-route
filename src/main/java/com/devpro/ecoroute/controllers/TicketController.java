package com.devpro.ecoroute.controllers;

import com.devpro.ecoroute.dtos.base.PagedResponseDTO;
import com.devpro.ecoroute.dtos.ticket.HeatmapPointDTO;
import com.devpro.ecoroute.dtos.ticket.TicketRequestDTO;
import com.devpro.ecoroute.dtos.ticket.TicketResponseDTO;
import com.devpro.ecoroute.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@Tag(name = "Tickets", description = "Endpoints para gerenciar chamados (tickets, incidentes etc)")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo ticket",
            description = "Cria um novo registro de ticket. Requer autenticação (Bearer Token).")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TicketResponseDTO> createTicket(
            @Valid @RequestBody TicketRequestDTO ticketRequestDTO
    ) {
        TicketResponseDTO createdTicket = ticketService.createTicket(ticketRequestDTO);

        // Cria a URL (Header 'Location') para o recurso recém-criado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTicket.id())
                .toUri();

        // Retorna 201 Created com a URL e o objeto criado no corpo
        return ResponseEntity.created(location).body(createdTicket);
    }

    @GetMapping
    @Operation(summary = "Lista todos os tickets")
    public ResponseEntity<PagedResponseDTO<TicketResponseDTO>> getAllUsers(
            // O Spring cria este objeto automaticamente a partir dos parâmetros da URL
            // @PageableDefault define os valores padrão se nenhum for enviado
            @ParameterObject @PageableDefault(size = 10, page = 0, sort = "createdAt") Pageable pageable
    ) {
        final PagedResponseDTO<TicketResponseDTO> response = ticketService.getAllTickets(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/heatmap")
    @Operation(summary = "Retorna dados para o mapa de calor",
            description = "Retorna uma lista de todos os chamados abertos com coordenadas válidas e seus respectivos pesos.")
    public ResponseEntity<List<HeatmapPointDTO>> getHeatmapData() {
        List<HeatmapPointDTO> data = ticketService.getHeatmapData();
        return ResponseEntity.ok(data);
    }
}