package com.academia.academiaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "presencas")
public class Presenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    private LocalTime horaCheckIn;

    private LocalTime horaCheckOut;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    @JsonIgnoreProperties("progresso") // evita loop se necessário
    private Aluno aluno;
}
