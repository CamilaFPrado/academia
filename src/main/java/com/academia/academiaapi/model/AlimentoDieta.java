package com.academia.academiaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "alimentos_dieta")
public class AlimentoDieta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeAlimento;

    @Column(nullable = false)
    private String refeicao;

    private Double imcMin;

    private Double imcMax;

    @Column
    private Integer calorias;

    private Double proteinas;

    private Double carboidratos;

    private Double gorduras;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno; // Relacionamento opcional com o aluno

    // Construtores (vazio e com argumentos)
    public AlimentoDieta() {
    }

    public AlimentoDieta(String nomeAlimento, String refeicao, Double imcMin, Double imcMax, Integer calorias, Double proteinas, Double carboidratos, Double gorduras, Aluno aluno) {
        this.nomeAlimento = nomeAlimento;
        this.refeicao = refeicao;
        this.imcMin = imcMin;
        this.imcMax = imcMax;
        this.calorias = calorias;
        this.proteinas = proteinas;
        this.carboidratos = carboidratos;
        this.gorduras = gorduras;
        this.aluno = aluno;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeAlimento() {
        return nomeAlimento;
    }

    public void setNomeAlimento(String nomeAlimento) {
        this.nomeAlimento = nomeAlimento;
    }

    public String getRefeicao() {
        return refeicao;
    }

    public void setRefeicao(String refeicao) {
        this.refeicao = refeicao;
    }

    public Double getImcMin() {
        return imcMin;
    }

    public void setImcMin(Double imcMin) {
        this.imcMin = imcMin;
    }

    public Double getImcMax() {
        return imcMax;
    }

    public void setImcMax(Double imcMax) {
        this.imcMax = imcMax;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    public Double getProteinas() {
        return proteinas;
    }

    public void setProteinas(Double proteinas) {
        this.proteinas = proteinas;
    }

    public Double getCarboidratos() {
        return carboidratos;
    }

    public void setCarboidratos(Double carboidratos) {
        this.carboidratos = carboidratos;
    }

    public Double getGorduras() {
        return gorduras;
    }

    public void setGorduras(Double gorduras) {
        this.gorduras = gorduras;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}