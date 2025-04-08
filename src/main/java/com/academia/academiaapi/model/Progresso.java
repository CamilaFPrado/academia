package com.academia.academiaapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "progresso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataRegistro;

    private Double peso; // em kg
    private Double altura; // em metros
    private Double percentualGordura; // opcional

    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
}
