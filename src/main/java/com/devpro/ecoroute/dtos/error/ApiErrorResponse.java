package com.devpro.ecoroute.dtos.error;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // PS: remove from json nullable fields
public record ApiErrorResponse(
        int status,
        String message,
        String param,
        String code
) {
    public ApiErrorResponse(int status, String message) {
        this(status, message, null, null);
    }

    public ApiErrorResponse(int status, String message, String code) {
        this(status, message, null, code);
    }
}