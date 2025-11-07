package com.devpro.ecoroute.dtos.wasteType;

import com.devpro.ecoroute.models.WasteType;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public record WasteTypeResponseDTO (
        Long id,
        String name,
        String description,
        String createdAt,
        String updatedAt,
        String deletedAt
) {
    public static WasteTypeResponseDTO fromWasteType(WasteType wasteType) {
        String deletedAtFormatted = Optional.ofNullable(wasteType.getDeletedAt())
                .map(dateTime -> dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .orElse(null);
        String createdAtFormatted = wasteType.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String updatedAtFormatted = wasteType.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return new WasteTypeResponseDTO(
                wasteType.getId(),
                wasteType.getName(),
                wasteType.getDescription(),
                createdAtFormatted,
                updatedAtFormatted,
                deletedAtFormatted
        );
    }
}
