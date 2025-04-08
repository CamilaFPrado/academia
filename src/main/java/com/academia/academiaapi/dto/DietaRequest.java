package com.academia.academiaapi.dto;

import java.util.Map;

public class DietaRequest {
    private Map<String, Long> alimentosPorRefeicao; // Key: nome da refeição, Value: ID do AlimentoDieta

    public Map<String, Long> getAlimentosPorRefeicao() {
        return alimentosPorRefeicao;
    }

    public void setAlimentosPorRefeicao(Map<String, Long> alimentosPorRefeicao) {
        this.alimentosPorRefeicao = alimentosPorRefeicao;
    }
}