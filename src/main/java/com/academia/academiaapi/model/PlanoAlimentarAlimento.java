package com.academia.academiaapi.model;

import jakarta.persistence.*;

@Entity
public class PlanoAlimentarAlimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plano_alimentar_id")
    private PlanoAlimentar planoAlimentar;

    @ManyToOne
    @JoinColumn(name = "alimento_dieta_id")
    private AlimentoDieta alimentoDieta;

    private String refeicao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlanoAlimentar getPlanoAlimentar() {
        return planoAlimentar;
    }

    public void setPlanoAlimentar(PlanoAlimentar planoAlimentar) {
        this.planoAlimentar = planoAlimentar;
    }

    public AlimentoDieta getAlimentoDieta() {
        return alimentoDieta;
    }

    public void setAlimentoDieta(AlimentoDieta alimentoDieta) {
        this.alimentoDieta = alimentoDieta;
    }

    public String getRefeicao() {
        return refeicao;
    }

    public void setRefeicao(String refeicao) {
        this.refeicao = refeicao;
    }
}