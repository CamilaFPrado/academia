package com.academia.academiaapi.service;

import com.academia.academiaapi.model.Exercicio;
import com.academia.academiaapi.model.Treino;
import com.academia.academiaapi.repository.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreinoService {

    @Autowired
    private TreinoRepository treinoRepository;

    public List<Treino> listarTodos() {
        return treinoRepository.findAll();
    }

    public Treino criarTreino(Treino treino) {
        // Garante que todos os exercícios recebam a referência do treino antes de salvar
        if (treino.getExercicios() != null) {
            for (Exercicio exercicio : treino.getExercicios()) {
                exercicio.setTreino(treino);
            }
        }
        return treinoRepository.save(treino);
    }

    public Treino atualizarTreino(Long id, Treino treino) {
        if (treinoRepository.existsById(id)) {
            treino.setId(id);
            if (treino.getExercicios() != null) {
                for (Exercicio exercicio : treino.getExercicios()) {
                    exercicio.setTreino(treino);
                }
            }
            return treinoRepository.save(treino);
        }
        return null;
    }

    public boolean deletarTreino(Long id) {
        if (treinoRepository.existsById(id)) {
            treinoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Treino> buscarPorImcComOverlapEDiaSemana(double minImcAluno, double maxImcAluno, String diaSemana) {
        return treinoRepository.findByImcOverlapAndDiaSemanaWithExercicios(minImcAluno, maxImcAluno, diaSemana);
    }
}
