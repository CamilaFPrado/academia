package com.academia.academiaapi.repository;

import com.academia.academiaapi.model.PlanoAlimentar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoAlimentarRepository extends JpaRepository<PlanoAlimentar, Long> {
    // Você pode adicionar métodos de consulta personalizados aqui, se necessário.
    // Por exemplo, para buscar planos alimentares por aluno:
    // List<PlanoAlimentar> findByAlunoId(Long alunoId);
}