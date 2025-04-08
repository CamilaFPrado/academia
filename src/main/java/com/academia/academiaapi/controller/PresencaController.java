package com.academia.academiaapi.controller;

import com.academia.academiaapi.model.Presenca;
import com.academia.academiaapi.service.PresencaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/presencas")
public class PresencaController {

    @Autowired
    private PresencaService presencaService;

    @PostMapping("/checkin/{alunoId}")
    public ResponseEntity<String> checkIn(@PathVariable Long alunoId) {
        String resposta = presencaService.registrarCheckIn(alunoId);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/checkout/{alunoId}")
    public ResponseEntity<String> checkOut(@PathVariable Long alunoId) {
        String resposta = presencaService.registrarCheckOut(alunoId);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<Presenca>> listarPorAluno(@PathVariable Long alunoId) {
        List<Presenca> lista = presencaService.listarPresencasPorAluno(alunoId);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/data")
    public ResponseEntity<List<Presenca>> listarPorData(
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<Presenca> lista = presencaService.listarPresencasPorData(data);
        return ResponseEntity.ok(lista);
    }
}
