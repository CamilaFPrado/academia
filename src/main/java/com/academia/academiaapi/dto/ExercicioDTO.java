package com.academia.academiaapi.dto;

import com.academia.academiaapi.model.Exercicio;

public class ExercicioDTO {
    private Long id;
    private String nome;
    private String grupoMuscular;
    private int series;
    private int repeticoes;

    // Construtor padrão (necessário para algumas frameworks de serialização/desserialização)
    public ExercicioDTO() {
    }

    public ExercicioDTO(Long id, String nome, String grupoMuscular, int series, int repeticoes) {
        this.id = id;
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
        this.series = series;
        this.repeticoes = repeticoes;
    }

    // Método estático para conversão de Exercicio para ExercicioDTO
    public static ExercicioDTO converterParaDTO(Exercicio exercicio) {
        System.out.println("    Convertendo Exercicio para DTO: " + exercicio.getId() + " - " + exercicio.getNome());
        return new ExercicioDTO(
                exercicio.getId(),
                exercicio.getNome(),
                exercicio.getGrupoMuscular(),
                exercicio.getSeries(),
                exercicio.getRepeticoes()
        );
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

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }
}