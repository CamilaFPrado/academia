package com.academia.academiaapi.controller;

import com.academia.academiaapi.model.Progresso;
import com.academia.academiaapi.service.ProgressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progressos")
public class ProgressoController {

    @Autowired
    private ProgressoService progressoService;

    @GetMapping
    public ResponseEntity<List<Progresso>> listarTodos() {
        return ResponseEntity.ok(progressoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Progresso> buscarPorId(@PathVariable Long id) {
        return progressoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<Progresso>> listarPorAluno(@PathVariable Long alunoId) {
        return ResponseEntity.ok(progressoService.listarPorAluno(alunoId));
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Progresso progresso) {
        return progressoService.cadastrar(progresso)
                .<ResponseEntity<?>>map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.badRequest().body("Aluno não encontrado"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Progresso progresso) {
        return progressoService.atualizar(id, progresso)
                .<ResponseEntity<?>>map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.badRequest().body("Progresso ou aluno não encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        progressoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
