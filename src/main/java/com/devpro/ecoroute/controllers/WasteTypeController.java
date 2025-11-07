package com.devpro.ecoroute.controllers;

import com.devpro.ecoroute.dtos.wasteType.WasteTypeResponseDTO;
import com.devpro.ecoroute.services.WasteTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/waste-types")
@Tag(name = "Waste Types", description = "Endpoints para consultar os tipos de resíduos")
public class WasteTypeController {
    private final WasteTypeService wasteTypeService;

    // Injeção de dependência via construtor (melhor prática)
    @Autowired
    public WasteTypeController(WasteTypeService wasteTypeService) {
        this.wasteTypeService = wasteTypeService;
    }
    
    @GetMapping("/search/by-prefix")
    @Operation(
            summary = "Buscar tipos de resíduo por prefixo do nome",
            description = "Retorna uma lista de tipos de resíduo onde o nome começa com o prefixo informado (case-insensitive)."
    )
    public ResponseEntity<List<WasteTypeResponseDTO>> searchByPrefix(
            @Parameter(description = "Prefixo para a busca (ex: 'rec').", required = true)
            @RequestParam("prefix") String prefix
    ) {
        List<WasteTypeResponseDTO> dtos = wasteTypeService.getWasteTypeByName(prefix);

        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    @Operation(summary = "Listar todos os tipos de resíduo",
            description = "Retorna a lista completa de todos os tipos de resíduo cadastrados.")
    public ResponseEntity<List<WasteTypeResponseDTO>> getAll() {
        List<WasteTypeResponseDTO> dtos = wasteTypeService.getAllWasteTypes();
        return ResponseEntity.ok(dtos);
    }
}