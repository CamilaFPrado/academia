package com.academia.academiaapi.controller;

import com.academia.academiaapi.dto.DietaRequest;
import com.academia.academiaapi.model.AlimentoDieta;
import com.academia.academiaapi.service.AlimentoDietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alimentacao")
public class AlimentoDietaController {

    @Autowired
    private AlimentoDietaService alimentoDietaService;

    @GetMapping("/imc/{imc}")
    public ResponseEntity<Map<String, List<AlimentoDieta>>> buscarAlimentosPorImc(@PathVariable double imc) {
        Map<String, List<AlimentoDieta>> alimentos = alimentoDietaService.buscarAlimentosPorImc(imc);
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/aluno/{alunoId}/imc/{imc}")
    public ResponseEntity<Map<String, List<AlimentoDieta>>> buscarAlimentosPorImcParaAluno(
            @PathVariable Long alunoId,
            @PathVariable double imc
    ) {
        Map<String, List<AlimentoDieta>> alimentos = alimentoDietaService.buscarAlimentosPorImcParaAluno(alunoId, imc);
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<AlimentoDieta>> listarTodosAlimentos() {
        List<AlimentoDieta> alimentos = alimentoDietaService.listarTodosAlimentos();
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentoDieta> buscarAlimentoPorId(@PathVariable Long id) {
        AlimentoDieta alimento = alimentoDietaService.buscarAlimentoPorId(id);
        return alimento != null ? ResponseEntity.ok(alimento) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AlimentoDieta> criarAlimento(@RequestBody AlimentoDieta alimentoDieta) {
        AlimentoDieta novoAlimento = alimentoDietaService.criarAlimento(alimentoDieta);
        return ResponseEntity.ok(novoAlimento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlimentoDieta> atualizarAlimento(@PathVariable Long id, @RequestBody AlimentoDieta alimentoDieta) {
        AlimentoDieta alimentoAtualizado = alimentoDietaService.atualizarAlimento(id, alimentoDieta);
        return alimentoAtualizado != null ? ResponseEntity.ok(alimentoAtualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAlimento(@PathVariable Long id) {
        boolean deletado = alimentoDietaService.deletarAlimento(id);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/aluno/{alunoId}/salvar-dieta")
    public ResponseEntity<?> salvarDieta(@PathVariable Long alunoId, @RequestBody DietaRequest dietaRequest) {
        try {
            alimentoDietaService.salvarDietaParaAluno(alunoId, dietaRequest);
            return ResponseEntity.ok("Dieta salva com sucesso para o aluno " + alunoId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar a dieta: " + e.getMessage());
        }
    }
}