package com.devpro.ecoroute.dtos.user;

import com.devpro.ecoroute.models.User;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String username,
        String name,
        String profile_pic,
        String createdAt,
        String updatedAt,
        String deletedAt
) {
    public static UserResponseDTO fromUser(User user) {
        String deletedAtFormatted = Optional.ofNullable(user.getDeletedAt())
                .map(dateTime -> dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .orElse(null);
        String createdAtFormatted = user.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String updatedAtFormatted = user.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getProfilePic(),
                createdAtFormatted,
                updatedAtFormatted,
                deletedAtFormatted
        );
    }
}
