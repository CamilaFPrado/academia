package com.academia.academiaapi.controller;

import com.academia.academiaapi.dto.ImcDTO;
import com.academia.academiaapi.model.Aluno;
import com.academia.academiaapi.repository.AlunoRepository;
import com.academia.academiaapi.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/imc")
public class ImcController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoService alunoService;

    // Retorna apenas o valor numérico do IMC
    @GetMapping("/aluno/{id}")
    public ResponseEntity<Double> calcularImc(@PathVariable Long id) {
        Double imc = alunoService.calcularImc(id);
        if (imc == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imc);
    }

    @PostMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarAlturaPeso(@PathVariable Long id, @RequestBody ImcDTO dto) {
        Optional<Aluno> alunoOptional = alunoRepository.findById(id);

        if (alunoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Aluno aluno = alunoOptional.get();
        aluno.setAltura(dto.getAltura());
        aluno.setPeso(dto.getPeso());

        if (dto.getAltura() != null && dto.getPeso() != null && dto.getAltura() > 0) {
            double imc = dto.getPeso() / (dto.getAltura() * dto.getAltura());
            aluno.setImc(imc);
            aluno.setUltimoImc(imc); // Atualiza os dois campos
        }

        alunoRepository.save(aluno);
        return ResponseEntity.ok("IMC atualizado com sucesso!");
    }



    // Calcula e salva o IMC no aluno (opcional)
    @PostMapping("/{alunoId}")
    public ResponseEntity<?> salvarImc(@PathVariable Long alunoId, @RequestBody ImcDTO imcDTO) {
        return alunoRepository.findById(alunoId).map(aluno -> {
            Double altura = aluno.getAltura();
            Double peso = aluno.getPeso();

            if (altura != null && peso != null && altura > 0 && peso > 0) {
                double imc = peso / (altura * altura);
                aluno.setUltimoImc(imc);
                alunoRepository.save(aluno);

                String categoria = calcularCategoriaIMC(imc);

                ImcDTO resultado = new ImcDTO();
                resultado.setImc(imc);
                resultado.setCategoria(categoria);

                return ResponseEntity.ok(resultado);
            }

            return ResponseEntity.badRequest().body("Dados inválidos para o cálculo do IMC");
        }).orElse(ResponseEntity.notFound().build());
    }

    private String calcularCategoriaIMC(double imc) {
        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc >= 18.5 && imc < 24.9) {
            return "Normal";
        } else if (imc >= 25 && imc < 29.9) {
            return "Sobrepeso";
        } else {
            return "Obesidade";
        }
    }
}
