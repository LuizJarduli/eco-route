package com.devpro.ecoroute.dtos.auth;

public record AuthResponseDTO(
        String accessToken,
        Long expirationTime
) { }
