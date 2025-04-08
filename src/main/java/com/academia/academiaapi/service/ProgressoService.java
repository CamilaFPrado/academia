package com.academia.academiaapi.service;

import com.academia.academiaapi.model.Aluno;
import com.academia.academiaapi.model.Progresso;
import com.academia.academiaapi.repository.AlunoRepository;
import com.academia.academiaapi.repository.ProgressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressoService {

    @Autowired
    private ProgressoRepository progressoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Progresso> listarTodos() {
        return progressoRepository.findAll();
    }

    public Optional<Progresso> buscarPorId(Long id) {
        return progressoRepository.findById(id);
    }

    public List<Progresso> listarPorAluno(Long alunoId) {
        return progressoRepository.findByAlunoId(alunoId);
    }

    public Optional<Progresso> cadastrar(Progresso progresso) {
        Optional<Aluno> aluno = alunoRepository.findById(progresso.getAluno().getId());

        if (aluno.isPresent()) {
            progresso.setAluno(aluno.get());
            return Optional.of(progressoRepository.save(progresso));
        }

        return Optional.empty();
    }

    public Optional<Progresso> atualizar(Long id, Progresso novoProgresso) {
        return progressoRepository.findById(id).map(progressoExistente -> {
            progressoExistente.setDataRegistro(novoProgresso.getDataRegistro());
            progressoExistente.setPeso(novoProgresso.getPeso());
            progressoExistente.setAltura(novoProgresso.getAltura());
            progressoExistente.setPercentualGordura(novoProgresso.getPercentualGordura());
            progressoExistente.setObservacoes(novoProgresso.getObservacoes());

            alunoRepository.findById(novoProgresso.getAluno().getId())
                    .ifPresent(progressoExistente::setAluno);

            return progressoRepository.save(progressoExistente);
        });
    }

    public void deletar(Long id) {
        progressoRepository.deleteById(id);
    }
}
