package com.academia.academiaapi.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AlunoDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private LocalDate dataCadastro;
    private Double altura;
    private Double peso;
    private Double ultimoImc;
}
