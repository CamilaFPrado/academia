package com.academia.academiaapi.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "aluno_id")  // Nome da chave estrangeira
    private Aluno aluno;

    private double imcMax;
    private double imcMin;
    private double calorias;
    private int duracao;
    private String diaSemana;

    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Exercicio> exercicios;


    // Construtores
    public Treino() {
    }

    public Treino(String nome, String descricao, double imcMax, double imcMin, double calorias, int duracao, String diaSemana, List<Exercicio> exercicios) {
        this.nome = nome;
        this.descricao = descricao;
        this.imcMax = imcMax;
        this.imcMin = imcMin;
        this.calorias = calorias;
        this.duracao = duracao;
        this.diaSemana = diaSemana;
        this.exercicios = exercicios;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getImcMax() {
        return imcMax;
    }

    public void setImcMax(double imcMax) {
        this.imcMax = imcMax;
    }

    public double getImcMin() {
        return imcMin;
    }

    public void setImcMin(double imcMin) {
        this.imcMin = imcMin;
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }
}
