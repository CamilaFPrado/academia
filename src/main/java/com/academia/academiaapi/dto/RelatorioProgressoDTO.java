package com.academia.academiaapi.dto;

import java.time.LocalDate;

public class RelatorioProgressoDTO {
    private LocalDate data;
    private Long alunoId;
    private double pesoInicial;
    private double pesoAtual;
    private double imc;
    private Double imcInicial;
    private Double imcFinal;
    private String alunoNome;
    private String emailAluno;

    // Construtor com todos os campos
    public RelatorioProgressoDTO(Long alunoId, double pesoInicial, double pesoAtual, double imc,
                                 Double imcInicial, Double imcFinal,
                                 String alunoNome, String emailAluno, LocalDate data) {
        this.alunoId = alunoId;
        this.pesoInicial = pesoInicial;
        this.pesoAtual = pesoAtual;
        this.imc = imc;
        this.imcInicial = imcInicial;
        this.imcFinal = imcFinal;
        this.alunoNome = alunoNome;
        this.emailAluno = emailAluno;
        this.data = data;
    }

    // Getters e Setters

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public String getAlunoNome() {
        return alunoNome;
    }

    public void setAlunoNome(String alunoNome) {
        this.alunoNome = alunoNome;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }


    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public double getPesoAtual() {
        return pesoAtual;
    }

    public void setPesoAtual(double pesoAtual) {
        this.pesoAtual = pesoAtual;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
