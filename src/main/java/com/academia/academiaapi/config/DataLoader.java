package com.academia.academiaapi.config;

import com.academia.academiaapi.model.Aluno;
import com.academia.academiaapi.service.AlunoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(AlunoService alunoService) {
        return args -> {
            alunoService.cadastrar(Aluno.builder()
                    .nome("João da Silva")
                    .email("joao@email.com")
                    .senha("123456")
                    .dataNascimento(LocalDate.of(1995, 5, 20))
                    .altura(1.75)
                    .peso(70.0)
                    .dataCadastro(LocalDate.now())
                    .build());

            alunoService.cadastrar(Aluno.builder()
                    .nome("Maria Souza")
                    .email("maria@email.com")
                    .senha("abcdef")
                    .dataNascimento(LocalDate.of(1990, 8, 15))
                    .altura(1.60)
                    .peso(60.0)
                    .dataCadastro(LocalDate.now())
                    .build());

            alunoService.cadastrar(Aluno.builder()
                    .nome("Carlos Lima")
                    .email("carlos@email.com")
                    .senha("senha123")
                    .dataNascimento(LocalDate.of(2000, 1, 10))
                    .altura(1.82)
                    .peso(80.5)
                    .dataCadastro(LocalDate.now())
                    .build());
        };
    }
}
