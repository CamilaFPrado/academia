package com.academia.academiaapi.service;

import com.academia.academiaapi.model.Aluno;
import com.academia.academiaapi.model.Presenca;
import com.academia.academiaapi.repository.AlunoRepository;
import com.academia.academiaapi.repository.PresencaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class PresencaService {

    @Autowired
    private PresencaRepository presencaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public String registrarCheckIn(Long alunoId) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(alunoId);
        if (alunoOpt.isEmpty()) {
            return "Aluno não encontrado.";
        }

        LocalDate hoje = LocalDate.now();
        Optional<Presenca> presencaOpt = presencaRepository.findByAlunoIdAndData(alunoId, hoje);

        if (presencaOpt.isPresent()) {
            return "Check-in já realizado hoje.";
        }

        Presenca presenca = new Presenca();
        presenca.setAluno(alunoOpt.get());
        presenca.setData(hoje);
        presenca.setHoraCheckIn(LocalTime.now());

        presencaRepository.save(presenca);
        return "Check-in registrado com sucesso.";
    }

    public String registrarCheckOut(Long alunoId) {
        LocalDate hoje = LocalDate.now();
        Optional<Presenca> presencaOpt = presencaRepository.findByAlunoIdAndData(alunoId, hoje);

        if (presencaOpt.isEmpty()) {
            return "Nenhum check-in encontrado para hoje.";
        }

        Presenca presenca = presencaOpt.get();

        if (presenca.getHoraCheckOut() != null) {
            return "Check-out já realizado hoje.";
        }

        presenca.setHoraCheckOut(LocalTime.now());
        presencaRepository.save(presenca);
        return "Check-out registrado com sucesso.";
    }

    public List<Presenca> listarPresencasPorAluno(Long alunoId) {
        return presencaRepository.findByAlunoId(alunoId);
    }

    public List<Presenca> listarPresencasPorData(LocalDate data) {
        return presencaRepository.findByData(data);
    }
}
