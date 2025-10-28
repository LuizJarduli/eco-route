package com.devpro.ecoroute.dtos.user;

import com.devpro.ecoroute.models.User;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public record UserResponseDTO(
        String id,
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
        return new UserResponseDTO(
                user.getId().toString(),
                user.getUsername(),
                user.getName(),
                user.getProfilePic(),
                user.getCreatedAt().format(DateTimeFormatter.ISO_DATE),
                user.getUpdatedAt().format(DateTimeFormatter.ISO_DATE),
                deletedAtFormatted
        );
    }
}
