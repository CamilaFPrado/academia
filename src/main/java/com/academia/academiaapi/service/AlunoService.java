package com.academia.academiaapi.service;

import com.academia.academiaapi.dto.AlunoDTO;
import com.academia.academiaapi.model.Aluno;
import com.academia.academiaapi.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public Aluno cadastrar(Aluno aluno) {
        Optional<Aluno> alunoExistente = alunoRepository.findByEmail(aluno.getEmail());
        if (alunoExistente.isPresent()) {
            return alunoExistente.get(); // ou lançar exceção personalizada
        }
        return alunoRepository.save(aluno);
    }

    public Double calcularImc(Long alunoId) {
        Optional<Aluno> alunoOptional = alunoRepository.findById(alunoId);
        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();
            if (aluno.getAltura() != null && aluno.getPeso() != null) {
                return aluno.getPeso() / Math.pow(aluno.getAltura(), 2);
            } else {
                throw new IllegalArgumentException("Altura ou peso não informado.");
            }
        } else {
            throw new NoSuchElementException("Aluno não encontrado.");
        }
    }



    public Optional<Aluno> atualizar(Long id, Aluno novoAluno) {
        return alunoRepository.findById(id).map(alunoExistente -> {
            alunoExistente.setNome(novoAluno.getNome());
            alunoExistente.setEmail(novoAluno.getEmail());
            alunoExistente.setDataNascimento(novoAluno.getDataNascimento());
            alunoExistente.setAltura(novoAluno.getAltura());
            alunoExistente.setPeso(novoAluno.getPeso());

            // Calcular IMC
            if (novoAluno.getAltura() != null && novoAluno.getPeso() != null && novoAluno.getAltura() > 0) {
                double imc = novoAluno.getPeso() / (novoAluno.getAltura() * novoAluno.getAltura());
                alunoExistente.setUltimoImc(imc);
            }

            return alunoRepository.save(alunoExistente);
        });
    }

    public void deletar(Long id) {
        alunoRepository.deleteById(id);
    }

    public AlunoDTO toDTO(Aluno aluno) {
        AlunoDTO dto = new AlunoDTO();
        dto.setId(aluno.getId());
        dto.setNome(aluno.getNome());
        dto.setEmail(aluno.getEmail());
        dto.setSenha(aluno.getSenha());
        dto.setDataNascimento(aluno.getDataNascimento());
        dto.setDataCadastro(aluno.getDataCadastro());
        dto.setAltura(aluno.getAltura());
        dto.setPeso(aluno.getPeso());
        dto.setUltimoImc(aluno.getUltimoImc());
        return dto;
    }

    public Aluno toEntity(AlunoDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setId(dto.getId());
        aluno.setNome(dto.getNome());
        aluno.setEmail(dto.getEmail());
        aluno.setSenha(dto.getSenha());
        aluno.setDataNascimento(dto.getDataNascimento());
        aluno.setDataCadastro(dto.getDataCadastro());
        aluno.setAltura(dto.getAltura());
        aluno.setPeso(dto.getPeso());
        aluno.setUltimoImc(dto.getUltimoImc());
        return aluno;
    }


    public void atribuirTreinoPorImc(Long id) {
        // Busca o aluno pelo ID
        Optional<Aluno> alunoOptional = alunoRepository.findById(id);

        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();

            // Verifica o IMC do aluno
            double imc = aluno.getUltimoImc();

            // Determina o treino com base no IMC
            String treino;
            if (imc < 18.5) {
                treino = "Treino de ganho de massa muscular (foco em força)";
            } else if (imc >= 18.5 && imc <= 24.9) {
                treino = "Treino equilibrado (foco em resistência e força)";
            } else if (imc >= 25 && imc <= 29.9) {
                treino = "Treino para redução de peso (foco em cardio)";
            } else {
                treino = "Treino para redução de peso (foco em cardio e circuitos)";
            }

            // Atribui o treino ao aluno
            aluno.setTreino(treino); // Vamos supor que você tenha um atributo "treino" no modelo de Aluno
            alunoRepository.save(aluno); // Salva a alteração do treino no banco
        } else {
            throw new NoSuchElementException("Aluno não encontrado");
        }
    }

}
