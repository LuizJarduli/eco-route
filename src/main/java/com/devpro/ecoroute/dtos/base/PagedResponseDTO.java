package com.devpro.ecoroute.dtos.base;

import org.springframework.data.domain.Page;
import java.util.List;

/**
 * @summary - DTO genérico para respostas paginadas
 */
public record PagedResponseDTO<T>(
        List<T> data,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
    public PagedResponseDTO(Page<T> page) {
        this(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}