package com.academia.academiaapi.dto;

import com.academia.academiaapi.model.Exercicio;
import com.academia.academiaapi.model.Treino;

import java.util.List;
import java.util.stream.Collectors;

public class TreinoDTO {
    private Long id;
    private String nome;
    private String diaSemana;
    private List<ExercicioDTO> exercicios; // Usando a lista de ExercicioDTO

    // Método estático para conversão
    public static TreinoDTO converterParaDTO(Treino treino) {
        System.out.println("Convertendo Treino: " + treino.getId() + " - " + treino.getNome());
        TreinoDTO dto = new TreinoDTO();
        dto.setId(treino.getId());
        dto.setNome(treino.getNome());
        dto.setDiaSemana(treino.getDiaSemana());
        List<ExercicioDTO> exercicioDTOs = treino.getExercicios().stream()
                .map(exercicio -> {
                    System.out.println("  Convertendo Exercicio: " + exercicio.getId() + " - " + exercicio.getNome());
                    return ExercicioDTO.converterParaDTO(exercicio);
                })
                .collect(Collectors.toList());
        dto.setExercicios(exercicioDTOs);
        return dto;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public List<ExercicioDTO> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<ExercicioDTO> exercicios) {
        this.exercicios = exercicios;
    }
}