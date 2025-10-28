package com.devpro.ecoroute.exceptions;

import com.devpro.ecoroute.dtos.error.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @summary - Captura a exceção ResourceNotFoundException.
     *
     * Retorna um status 404 (Not Found) e o corpo de erro padronizado.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(), // 404
                ex.getMessage(),
                "RESOURCE_NOT_FOUND" // O 'code' que você queria
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * @summary - Captura genérica para qualquer outra exceção não tratada.
     * Retorna um status 500 (Internal Server Error).
     * É importante logar este erro, pois é um bug inesperado.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
        // FIXME: use logging later
        ex.printStackTrace();

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Ocorreu um erro interno inesperado no servidor.",
                "INTERNAL_SERVER_ERROR"
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}