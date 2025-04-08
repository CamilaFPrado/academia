package com.academia.academiaapi.controller;

import com.academia.academiaapi.dto.AlunoDTO;
import com.academia.academiaapi.model.Aluno;
import com.academia.academiaapi.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    // Listar todos os alunos
    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listarTodos() {
        List<AlunoDTO> alunos = alunoService.listarTodos()
                .stream()
                .map(alunoService::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(alunos);
    }

    // Buscar aluno por ID
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscarPorId(@PathVariable Long id) {
        Optional<Aluno> alunoOpt = alunoService.buscarPorId(id);

        return alunoOpt
                .map(alunoService::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cadastrar novo aluno
    @PostMapping
    public ResponseEntity<AlunoDTO> cadastrar(@RequestBody AlunoDTO alunoDTO) {
        Aluno aluno = alunoService.toEntity(alunoDTO);
        aluno.setDataCadastro(LocalDate.now()); // define a data de cadastro aqui
        Aluno salvo = alunoService.cadastrar(aluno);

        // Chama o método para atribuir treino com base no IMC após o aluno ser salvo
        alunoService.atribuirTreinoPorImc(salvo.getId());

        return ResponseEntity.ok(alunoService.toDTO(salvo));
    }

    // Atualizar aluno
    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizar(@PathVariable Long id, @RequestBody AlunoDTO alunoDTO) {
        Aluno novoAluno = alunoService.toEntity(alunoDTO);
        Optional<Aluno> atualizado = alunoService.atualizar(id, novoAluno);

        if (atualizado.isPresent()) {
            // Chama o método para atribuir ou atualizar o treino com base no IMC
            alunoService.atribuirTreinoPorImc(atualizado.get().getId());
            return ResponseEntity.ok(alunoService.toDTO(atualizado.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar aluno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar IMC de um aluno
    @GetMapping("/{alunoId}/imc")
    public ResponseEntity<Double> buscarImc(@PathVariable Long alunoId) {
        Optional<Aluno> alunoOptional = alunoService.buscarPorId(alunoId);
        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();
            Double imc = aluno.calcularIMC(); // Calcula o IMC
            return ResponseEntity.ok(imc); // Retorna o IMC
        } else {
            return ResponseEntity.notFound().build(); // Caso o aluno não seja encontrado
        }
    }
}
