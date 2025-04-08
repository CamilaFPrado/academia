package com.academia.academiaapi.repository;

import com.academia.academiaapi.model.PlanoAlimentarAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoAlimentarAlimentoRepository extends JpaRepository<PlanoAlimentarAlimento, Long> {
    // Você pode adicionar métodos de consulta personalizados aqui, se necessário.
    // Por exemplo, para buscar todos os alimentos de um plano alimentar:
    // List<PlanoAlimentarAlimento> findByPlanoAlimentarId(Long planoAlimentarId);
}