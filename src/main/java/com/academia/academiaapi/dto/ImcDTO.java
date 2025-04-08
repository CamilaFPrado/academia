package com.academia.academiaapi.dto;

import lombok.Data;

@Data
public class ImcDTO {
    private Double altura;
    private Double peso;

    private double imc;
    private String categoria;
}
