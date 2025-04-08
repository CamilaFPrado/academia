document.addEventListener("DOMContentLoaded", () => {
    carregarAlunos();
    carregarDias(); // Carregar os dias da semana no select

    const listaAlunosSelect = document.getElementById("listaAlunos");
    const listaDiasSelect = document.getElementById("ListaDias"); // Corrigi o ID para "ListaDias"

    if (listaAlunosSelect) {
        listaAlunosSelect.addEventListener("change", async function (event) {
            const alunoId = event.target.value;
            if (!alunoId) return;

            try {
                const resposta = await fetch(`http://localhost:8080/api/alunos/${alunoId}`);
                if (!resposta.ok) throw new Error("Erro ao buscar aluno");

                const aluno = await resposta.json();
                if (!aluno.ultimoImc) {
                    console.error("IMC não disponível para este aluno.");
                    return;
                }

                if (listaDiasSelect && listaDiasSelect.value) {
                    carregarImcETreinos(aluno.ultimoImc, listaDiasSelect.value);
                } else {
                    console.warn("Nenhum dia da semana selecionado inicialmente.");
                }

            } catch (erro) {
                console.error("Erro ao buscar dados do aluno:", erro);
            }
        });
    } else {
        console.error("Elemento com ID 'listaAlunos' não encontrado.");
    }

    if (listaDiasSelect) {
        listaDiasSelect.addEventListener("change", async function (event) {
            const diaSelecionado = event.target.value;
            const alunoSelecionadoId = document.getElementById("listaAlunos")?.value; // Use optional chaining

            if (alunoSelecionadoId) {
                try {
                    const resposta = await fetch(`http://localhost:8080/api/alunos/${alunoSelecionadoId}`);
                    if (!resposta.ok) throw new Error("Erro ao buscar aluno");

                    const aluno = await resposta.json();
                    if (aluno.ultimoImc) {
                        carregarImcETreinos(aluno.ultimoImc, formatarDiaParaBackend(diaSelecionado)); // Formatar para o backend
                    }
                } catch (erro) {
                    console.error("Erro ao buscar dados do aluno:", erro);
                }
            }
        });
    } else {
        console.error("Elemento com ID 'ListaDias' não encontrado.");
    }
});

async function carregarAlunos() {
    const select = document.getElementById("listaAlunos");

    try {
        const response = await fetch("http://localhost:8080/api/alunos");
        if (!response.ok) throw new Error("Erro ao carregar alunos");

        const alunos = await response.json();
        select.innerHTML = '<option value="">Selecione um aluno</option>';

        alunos.forEach(aluno => {
            const option = document.createElement("option");
            option.value = aluno.id;
            option.textContent = aluno.nome;
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao carregar alunos:", error);
        select.innerHTML = `<option value="">Erro: ${error.message}</option>`;
    }
}

async function carregarDias() {
    const select = document.getElementById("ListaDias"); // Corrigi o ID para "ListaDias"
    const diasDaSemana = ["Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"];
    const valoresBackend = ["SEGUNDA_FEIRA", "TERCA_FEIRA", "QUARTA_FEIRA", "QUINTA_FEIRA", "SEXTA_FEIRA", "SABADO", "DOMINGO"];

    if (select) {
        select.innerHTML = '<option value="">Selecione o dia</option>';
        diasDaSemana.forEach((dia, index) => {
            const option = document.createElement("option");
            option.value = dia; // Usar o nome completo para exibição
            option.dataset.backendValue = valoresBackend[index]; // Armazenar o valor para o backend
            option.textContent = dia;
            select.appendChild(option);
        });
    } else {
        console.error("Elemento com ID 'ListaDias' não encontrado.");
    }
}

function formatarDiaParaBackend(diaFrontend) {
    switch (diaFrontend) {
        case "Segunda-feira": return "SEGUNDA_FEIRA";
        case "Terça-feira": return "TERCA_FEIRA";
        case "Quarta-feira": return "QUARTA_FEIRA";
        case "Quinta-feira": return "QUINTA_FEIRA";
        case "Sexta-feira": return "SEXTA_FEIRA";
        case "Sábado": return "SABADO";
        case "Domingo": return "DOMINGO";
        default: return "";
    }
}

function categoriaImc(imc) {
    if (imc < 18.5) return "Baixo Peso";
    if (imc >= 18.5 && imc <= 24.9) return "Peso Normal";
    if (imc >= 25.0 && imc <= 29.9) return "Sobrepeso";
    if (imc >= 30) return "Obesidade";
    return "Categoria desconhecida";
}

async function carregarImcETreinos(imc, diaSelecionadoBackend) {
    if (isNaN(imc)) {
        console.error("Valor de IMC inválido:", imc);
        return;
    }

    if (!diaSelecionadoBackend) {
        console.warn("Nenhum dia da semana selecionado ao carregar treinos.");
        return;
    }

    const categoria = categoriaImc(imc);
    console.log(`Categoria IMC: ${categoria}, IMC: ${imc}, Dia (Backend): ${diaSelecionadoBackend}`);

    let min, max;

    switch (categoria) {
        case "Baixo Peso":
            min = 0;
            max = 18.4;
            break;
        case "Peso Normal":
            min = 18.5;
            max = 24.9;
            break;
        case "Sobrepeso":
            min = 25.0;
            max = 29.9;
            break;
        case "Obesidade":
            min = 30;
            max = 100;
            break;
        default:
            console.error("Categoria não reconhecida.");
            return;
    }

    try {
        const url = `/api/treinos/imc/overlap?min=${min}&max=${max}&dia=${diaSelecionadoBackend}`;
        const response = await fetch(url);
        if (!response.ok) {
            const errorData = await response.json();
            console.error("Erro ao carregar treinos:", errorData);
            alert(`Erro ao carregar treinos: ${response.status} - ${errorData?.message || response.statusText}`);
            return;
        }
        const treinos = await response.json();

        console.log("Treinos recebidos:", treinos);

        const treinoContainer = document.getElementById("treino-container");
        if (treinoContainer) {
            exibirTreinos(treinos);
        } else {
            console.error("Elemento com ID 'treino-container' não encontrado.");
        }

    } catch (error) {
        console.error("Erro ao carregar treinos:", error);
        alert(error.message);
    }
}

function exibirTreinos(treinos) {
    const treinoContainer = document.getElementById("treino-container");
    if (!treinoContainer) {
        console.error("Elemento com ID 'treino-container' não encontrado.");
        return;
    }
    treinoContainer.innerHTML = "";

    if (treinos.length === 0) {
        treinoContainer.innerHTML = "<p>Nenhum treino disponível para esse intervalo de IMC e dia da semana.</p>";
        return;
    }

    treinos.forEach(treino => {
        const treinoCard = document.createElement("div");
        treinoCard.classList.add("treino-card");

        const titulo = document.createElement("h3");
        titulo.textContent = treino.nome;
        treinoCard.appendChild(titulo);

        if (treino.exercicios && treino.exercicios.length > 0) {
            const lista = document.createElement("ul");
            treino.exercicios.forEach(exercicio => {
                const item = document.createElement("li");
                item.textContent = `${exercicio.nome} - ${exercicio.repeticoes} repetições - ${exercicio.series} séries - ${exercicio.grupoMuscular}`;
                lista.appendChild(item);
            });
            treinoCard.appendChild(lista);
        } else {
            const semExercicios = document.createElement("p");
            semExercicios.textContent = "Este treino ainda não possui exercícios cadastrados.";
            treinoCard.appendChild(semExercicios);
        }

        treinoContainer.appendChild(treinoCard);
    });
}