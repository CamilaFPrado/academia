package com.academia.academiaapi.service;

import com.academia.academiaapi.dto.DietaRequest;
import com.academia.academiaapi.model.AlimentoDieta;
import com.academia.academiaapi.model.Aluno;
import com.academia.academiaapi.model.PlanoAlimentar;
import com.academia.academiaapi.model.PlanoAlimentarAlimento;
import com.academia.academiaapi.repository.AlimentoDietaRepository;
import com.academia.academiaapi.repository.AlunoRepository;
import com.academia.academiaapi.repository.PlanoAlimentarAlimentoRepository;
import com.academia.academiaapi.repository.PlanoAlimentarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlimentoDietaService {

    @Autowired
    private AlimentoDietaRepository alimentoDietaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PlanoAlimentarRepository planoAlimentarRepository;

    @Autowired
    private PlanoAlimentarAlimentoRepository planoAlimentarAlimentoRepository;

    private final String[] refeicoes = {"Cafe da Manha", "Lanche Manha", "Almoco", "Lanche Tarde", "Jantar", "Ceia"};

    public Map<String, List<AlimentoDieta>> buscarAlimentosPorImc(double imc) {
        Map<String, List<AlimentoDieta>> alimentosPorRefeicao = new HashMap<>();

        for (String refeicao : refeicoes) {
            List<AlimentoDieta> alimentos = alimentoDietaRepository
                    .findByImcMinLessThanEqualAndImcMaxGreaterThanEqualAndRefeicaoIgnoreCase(imc, imc, refeicao);
            alimentosPorRefeicao.put(refeicao, alimentos);
        }

        return alimentosPorRefeicao;
    }

    public Map<String, List<AlimentoDieta>> buscarAlimentosPorImcParaAluno(Long alunoId, double imc) {
        Map<String, List<AlimentoDieta>> alimentosPorRefeicao = new HashMap<>();

        for (String refeicao : refeicoes) {
            List<AlimentoDieta> alimentos = alimentoDietaRepository
                    .findByAlunoIdAndImcMinLessThanEqualAndImcMaxGreaterThanEqualAndRefeicaoIgnoreCase(
                            alunoId, imc, imc, refeicao
                    );
            if (alimentos.isEmpty()) {
                alimentos = alimentoDietaRepository
                        .findByImcMinLessThanEqualAndImcMaxGreaterThanEqualAndRefeicaoIgnoreCase(imc, imc, refeicao);
            }
            alimentosPorRefeicao.put(refeicao, alimentos);
        }

        return alimentosPorRefeicao;
    }

    public List<AlimentoDieta> listarTodosAlimentos() {
        return alimentoDietaRepository.findAll();
    }

    public AlimentoDieta buscarAlimentoPorId(Long id) {
        return alimentoDietaRepository.findById(id).orElse(null);
    }

    public AlimentoDieta criarAlimento(AlimentoDieta alimentoDieta) {
        return alimentoDietaRepository.save(alimentoDieta);
    }

    public AlimentoDieta atualizarAlimento(Long id, AlimentoDieta alimentoDieta) {
        if (alimentoDietaRepository.existsById(id)) {
            alimentoDieta.setId(id);
            return alimentoDietaRepository.save(alimentoDieta);
        }
        return null;
    }

    public boolean deletarAlimento(Long id) {
        if (alimentoDietaRepository.existsById(id)) {
            alimentoDietaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public void salvarDietaParaAluno(Long alunoId, DietaRequest dietaRequest) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + alunoId));

        PlanoAlimentar planoAlimentar = new PlanoAlimentar();
        planoAlimentar.setAluno(aluno);
        PlanoAlimentar planoSalvo = planoAlimentarRepository.save(planoAlimentar);

        Map<String, Long> alimentosPorRefeicao = dietaRequest.getAlimentosPorRefeicao();

        for (Map.Entry<String, Long> entry : alimentosPorRefeicao.entrySet()) {
            String refeicao = entry.getKey();
            Long alimentoId = entry.getValue();

            if (alimentoId != null) { // Garante que um alimento foi selecionado para a refeição
                AlimentoDieta alimento = alimentoDietaRepository.findById(alimentoId)
                        .orElseThrow(() -> new RuntimeException("Alimento não encontrado com ID: " + alimentoId));

                PlanoAlimentarAlimento associacao = new PlanoAlimentarAlimento();
                associacao.setPlanoAlimentar(planoSalvo);
                associacao.setAlimentoDieta(alimento);
                associacao.setRefeicao(refeicao);
                planoAlimentarAlimentoRepository.save(associacao);
            }
        }
    }
}