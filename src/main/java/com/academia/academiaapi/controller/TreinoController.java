package com.academia.academiaapi.controller;

import com.academia.academiaapi.dto.TreinoDTO;
import com.academia.academiaapi.model.Exercicio;
import com.academia.academiaapi.model.Treino;
import com.academia.academiaapi.repository.TreinoRepository;
import com.academia.academiaapi.service.TreinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/treinos")
public class TreinoController {

    @Autowired
    private TreinoService treinoService;

    // Listar todos os treinos (convertendo para DTO)
    @GetMapping("/treinos")
    public List<TreinoDTO> listarTreinos() {
        List<Treino> treinos = treinoService.listarTodos();

        for (Treino treino : treinos) {
            System.out.println("Treino: " + treino.getNome());
            System.out.println("Exercícios: " + treino.getExercicios());
        }

        return treinos.stream()
                .map(TreinoDTO::converterParaDTO)
                .collect(Collectors.toList());
    }



    // Método auxiliar para converter Treino para TreinoDTO


    // Criar novo treino
    @PostMapping
    public ResponseEntity<Treino> criarTreino(@RequestBody Treino treino) {
        Treino novoTreino = treinoService.criarTreino(treino);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTreino);
    }

    // Atualizar treino
    @PutMapping("/{id}")
    public ResponseEntity<Treino> atualizarTreino(@PathVariable Long id, @RequestBody Treino treino) {
        Treino treinoAtualizado = treinoService.atualizarTreino(id, treino);
        return treinoAtualizado != null ? ResponseEntity.ok(treinoAtualizado) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Excluir treino
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTreino(@PathVariable Long id) {
        boolean deleted = treinoService.deletarTreino(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Filtrar treinos por IMC
    @GetMapping("/imc/overlap") // Removendo os PathVariables min e max
    public ResponseEntity<List<TreinoDTO>> getTreinosPorImcComOverlapEDiaSemana(
            @RequestParam double min,
            @RequestParam double max,
            @RequestParam String dia
    ) {
        List<Treino> treinos = treinoService.buscarPorImcComOverlapEDiaSemana(min, max, dia);
        List<TreinoDTO> dtos = treinos.stream()
                .map(TreinoDTO::converterParaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
