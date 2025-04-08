package com.academia.academiaapi.service;

import com.academia.academiaapi.dto.RelatorioProgressoDTO;
import com.academia.academiaapi.model.Progresso;
import com.academia.academiaapi.repository.ProgressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class RelatorioService {

    @Autowired
    private ProgressoRepository progressoRepository;

    public RelatorioProgressoDTO gerarRelatorioProgresso(Long alunoId) {
        List<Progresso> registros = progressoRepository.findByAlunoId(alunoId);

        if (registros.isEmpty()) {
            return null;
        }

        registros.sort(Comparator.comparing(Progresso::getDataRegistro));

        OptionalDouble pesoMedio = registros.stream().mapToDouble(Progresso::getPeso).average();
        OptionalDouble alturaMedia = registros.stream().mapToDouble(Progresso::getAltura).average();
        OptionalDouble gorduraMedia = registros.stream().mapToDouble(p -> p.getPercentualGordura() != null ? p.getPercentualGordura() : 0).average();

        Progresso primeiro = registros.get(0);
        Progresso ultimo = registros.get(registros.size() - 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return new RelatorioProgressoDTO(
                alunoId, // alunoId (Long)
                primeiro.getPeso(), // pesoInicial (double)
                ultimo.getPeso(), // pesoAtual (double)
                ultimo.getPeso() / (alturaMedia.orElse(1.0) * alturaMedia.orElse(1.0)), // imc calculado
                primeiro.getPeso() / (alturaMedia.orElse(1.0) * alturaMedia.orElse(1.0)), // imcInicial
                ultimo.getPeso() / (alturaMedia.orElse(1.0) * alturaMedia.orElse(1.0)), // imcFinal
                primeiro.getAluno().getNome(), // alunoNome (String)
                primeiro.getAluno().getEmail(), // emailAluno (String)
                ultimo.getDataRegistro() // data (LocalDate)
        );

    }
}
