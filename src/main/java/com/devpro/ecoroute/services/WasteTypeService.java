package com.devpro.ecoroute.services;

import com.devpro.ecoroute.dtos.wasteType.WasteTypeResponseDTO;
import com.devpro.ecoroute.models.WasteType;
import com.devpro.ecoroute.repositories.WasteTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WasteTypeService {
    private final WasteTypeRepository wasteTypeRepository;

    @Autowired
    public WasteTypeService(WasteTypeRepository wasteTypeRepository) {
        this.wasteTypeRepository = wasteTypeRepository;
    }

    public List<WasteTypeResponseDTO> getWasteTypeByName(String prefix) {
        final List<WasteType> wasteTypes = wasteTypeRepository.findByNameStartingWithIgnoreCase(prefix);

        return wasteTypes.stream().map(WasteTypeResponseDTO::fromWasteType).toList();
    }

    public List<WasteTypeResponseDTO> getAllWasteTypes() {
        return wasteTypeRepository.findAll()
                .stream()
                .map(WasteTypeResponseDTO::fromWasteType)
                .toList();
    }
}
