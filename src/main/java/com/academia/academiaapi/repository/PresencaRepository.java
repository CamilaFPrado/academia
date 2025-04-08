package com.academia.academiaapi.repository;

import com.academia.academiaapi.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PresencaRepository extends JpaRepository<Presenca, Long> {

    List<Presenca> findByAlunoId(Long alunoId);

    List<Presenca> findByData(LocalDate data);

    Optional<Presenca> findByAlunoIdAndData(Long alunoId, LocalDate data);
}
