package com.academia.academiaapi.service;

import com.academia.academiaapi.model.Professor;
import com.academia.academiaapi.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public List<Professor> listarTodos() {
        return professorRepository.findAll();
    }

    public Optional<Professor> buscarPorId(Long id) {
        return professorRepository.findById(id);
    }

    public Professor cadastrar(Professor professor) {
        return professorRepository.save(professor);
    }

    public Optional<Professor> atualizar(Long id, Professor novoProfessor) {
        return professorRepository.findById(id).map(professorExistente -> {
            professorExistente.setNome(novoProfessor.getNome());
            professorExistente.setEmail(novoProfessor.getEmail());
            professorExistente.setEspecialidade(novoProfessor.getEspecialidade());
            professorExistente.setTelefone(novoProfessor.getTelefone());
            return professorRepository.save(professorExistente);
        });
    }

    public void deletar(Long id) {
        professorRepository.deleteById(id);
    }
}
