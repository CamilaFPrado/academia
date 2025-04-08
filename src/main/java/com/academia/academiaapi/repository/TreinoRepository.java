package com.academia.academiaapi.repository;

import com.academia.academiaapi.model.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TreinoRepository extends JpaRepository<Treino, Long> {

    @Query("SELECT t FROM Treino t WHERE t.imcMin <= :imcMaxAluno AND t.imcMax >= :imcMinAluno")
    List<Treino> findByImcOverlap(@Param("imcMinAluno") double imcMinAluno, @Param("imcMaxAluno") double imcMaxAluno);

    @Query("SELECT t FROM Treino t LEFT JOIN FETCH t.exercicios WHERE t.imcMin <= :imcMaxAluno AND t.imcMax >= :imcMinAluno")
    List<Treino> findByImcOverlapWithExercicios(@Param("imcMinAluno") double imcMinAluno, @Param("imcMaxAluno") double imcMaxAluno);

    @Query("SELECT t FROM Treino t LEFT JOIN FETCH t.exercicios WHERE t.imcMin <= :imcMaxAluno AND t.imcMax >= :imcMinAluno AND t.diaSemana = :diaSemana")
    List<Treino> findByImcOverlapAndDiaSemanaWithExercicios(
            @Param("imcMinAluno") double imcMinAluno,
            @Param("imcMaxAluno") double imcMaxAluno,
            @Param("diaSemana") String diaSemana
    );
    // ... seus outros métodos ...
}