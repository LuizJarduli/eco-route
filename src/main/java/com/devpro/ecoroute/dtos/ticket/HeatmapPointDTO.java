package com.devpro.ecoroute.dtos.ticket;

import java.math.BigDecimal;

// Um 'record' simples para cada ponto no mapa
public record HeatmapPointDTO(
        BigDecimal latitude,
        BigDecimal longitude,
        int weight // "Peso" do ponto (ex: prioridade ALTA=3, MEDIA=2, BAIXA=1)
) {}