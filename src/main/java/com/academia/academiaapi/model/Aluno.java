package com.academia.academiaapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "alunos")
@Data // Lombok vai gerar getters, setters, toString, equals e hashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;

    private LocalDate dataNascimento;

    private Double altura;

    private Double peso;

    private Double imc;

    private LocalDate dataCadastro;

    @OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)  // Define o relacionamento
    private List<Treino> treinos;  // Lista de treinos associados ao aluno


    // Métodos para acessar o último IMC
    @Setter
    @Getter
    private Double ultimoImc;

    // Cálculo do IMC
    public Double calcularIMC() {
        if (peso != null && altura != null && altura > 0) {
            return peso / (altura * altura);
        }
        return null; // Retorna null caso o peso ou a altura não estejam definidos corretamente
    }

    // Novo método para adicionar um treino à lista de treinos do aluno
    public void adicionarTreino(Treino treino) {
        this.treinos.add(treino);
    }

    public void setTreino(String treino) {
        // Método não necessário, pois a lista de treinos já está gerenciada com add()
    }
}
