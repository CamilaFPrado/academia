package com.academia.academiaapi.repository;

import com.academia.academiaapi.model.AlimentoDieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlimentoDietaRepository extends JpaRepository<AlimentoDieta, Long> {

    List<AlimentoDieta> findByImcMinLessThanEqualAndImcMaxGreaterThanEqualAndRefeicaoIgnoreCase(
            double imcMin, double imcMax, String refeicao
    );

    List<AlimentoDieta> findByAlunoIdAndImcMinLessThanEqualAndImcMaxGreaterThanEqualAndRefeicaoIgnoreCase(
            Long alunoId, double imcMin, double imcMax, String refeicao
    );

    List<AlimentoDieta> findByImcMinLessThanEqualAndImcMaxGreaterThanEqual(double imcMin, double imcMax);

    List<AlimentoDieta> findByRefeicaoIgnoreCase(String refeicao);

    List<AlimentoDieta> findAllByAluno_Id(Long alunoId);
}