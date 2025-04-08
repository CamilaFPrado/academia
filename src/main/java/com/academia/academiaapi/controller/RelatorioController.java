package com.academia.academiaapi.controller;

import com.academia.academiaapi.dto.RelatorioProgressoDTO;
import com.academia.academiaapi.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/progresso/{alunoId}")
    public ResponseEntity<?> gerarRelatorioProgresso(@PathVariable Long alunoId) {
        RelatorioProgressoDTO relatorio = relatorioService.gerarRelatorioProgresso(alunoId);

        if (relatorio == null) {
            return ResponseEntity.badRequest().body("Nenhum progresso encontrado para o aluno.");
        }

        return ResponseEntity.ok(relatorio);
    }
}
