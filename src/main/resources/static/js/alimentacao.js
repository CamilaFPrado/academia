async function carregarDadosAlimentacao(imc, alunoId = null) {
    if (isNaN(imc)) {
        console.error("IMC inválido para carregar alimentação:", imc);
        return;
    }

    let url = `/api/alimentacao/imc/${imc}`;
    if (alunoId) {
        url = `/api/alimentacao/aluno/${alunoId}/imc/${imc}`;
    }

    try {
        const response = await fetch(url);
        if (!response.ok) {
            const errorData = await response.json();
            console.error("Erro ao carregar dados de alimentação:", errorData);
            alert(`Erro ao carregar alimentação: ${response.status} - ${errorData?.message || response.statusText}`);
            return;
        }
        const alimentosPorRefeicao = await response.json();
        exibirDadosAlimentacao(alimentosPorRefeicao);

    } catch (error) {
        console.error("Erro ao buscar dados de alimentação:", error);
        alert(error.message);
    }
}

let todosAlimentos = {}; // Objeto para armazenar todos os alimentos com suas calorias
let escolhasAluno = {};
let totalCalorias = 0;

function exibirDadosAlimentacao(alimentosPorRefeicao) {
    const alimentacaoContainer = document.getElementById("alimentacao-container");
    if (!alimentacaoContainer) {
        console.error("Elemento com ID 'alimentacao-container' não encontrado.");
        return;
    }
    alimentacaoContainer.innerHTML = "";
    todosAlimentos = {}; // Limpa os alimentos anteriores
    escolhasAluno = {};
    totalCalorias = 0;
    document.getElementById("total-calorias").querySelector('span').textContent = totalCalorias;
    document.getElementById("salvarDieta").style.display = 'none'; // Oculta o botão de salvar por enquanto

    const refeicoesOrdenadas = ["Cafe da Manha", "Lanche Manha", "Almoco", "Lanche Tarde", "Jantar", "Ceia"];

    refeicoesOrdenadas.forEach(refeicao => {
        const refeicaoDiv = document.createElement("div");
        refeicaoDiv.classList.add("refeicao");

        const tituloRefeicao = document.createElement("h3");
        tituloRefeicao.textContent = refeicao;
        refeicaoDiv.appendChild(tituloRefeicao);

        const selectAlimento = document.createElement("select");
        selectAlimento.id = `select-${refeicao.toLowerCase().replace(/ /g, '-')}`;
        const alimentos = alimentosPorRefeicao[refeicao];

        const opcaoPadrao = document.createElement("option");
        opcaoPadrao.value = "";
        opcaoPadrao.textContent = "Selecione um alimento";
        selectAlimento.appendChild(opcaoPadrao);

        if (alimentos && alimentos.length > 0) {
            alimentos.forEach(alimento => {
                const option = document.createElement("option");
                option.value = alimento.id; // Usar o ID como valor para facilitar a busca das calorias
                option.textContent = alimento.nomeAlimento;
                selectAlimento.appendChild(option);
                todosAlimentos[alimento.id] = alimento; // Armazena o alimento completo
            });
        } else {
            const opcaoNenhum = document.createElement("option");
            opcaoNenhum.value = "";
            opcaoNenhum.textContent = "Nenhum alimento recomendado";
            selectAlimento.appendChild(opcaoNenhum);
            selectAlimento.disabled = true;
        }

        selectAlimento.addEventListener('change', (event) => {
            atualizarCalorias(refeicao, event.target.value);
        });

        refeicaoDiv.appendChild(selectAlimento);
        alimentacaoContainer.appendChild(refeicaoDiv);
    });

    if (Object.keys(todosAlimentos).length > 0) {
        document.getElementById("salvarDieta").style.display = 'block'; // Mostra o botão de salvar se houver alimentos
    }
}

function atualizarCalorias(refeicao, alimentoId) {
    console.log('Atualizando calorias para', refeicao, 'com alimento ID:', alimentoId);
    if (alimentoId && todosAlimentos[alimentoId] && todosAlimentos[alimentoId].calorias) {
        console.log('Alimento válido encontrado.');
        if (escolhasAluno[refeicao] && todosAlimentos[escolhasAluno[refeicao]] && todosAlimentos[escolhasAluno[refeicao]].calorias) {
            console.log('Subtraindo calorias do alimento anterior:', todosAlimentos[escolhasAluno[refeicao]].calorias);
            totalCalorias -= parseFloat(todosAlimentos[escolhasAluno[refeicao]].calorias);
        }
        escolhasAluno[refeicao] = alimentoId;
        console.log('Adicionando calorias do novo alimento:', todosAlimentos[alimentoId].calorias);
        totalCalorias += parseFloat(todosAlimentos[alimentoId].calorias);
    } else if (escolhasAluno[refeicao] && todosAlimentos[escolhasAluno[refeicao]] && todosAlimentos[escolhasAluno[refeicao]].calorias) {
        console.log('Alimento deselecionado ou inválido. Subtraindo calorias do anterior:', todosAlimentos[escolhasAluno[refeicao]].calorias);
        totalCalorias -= parseFloat(todosAlimentos[escolhasAluno[refeicao]].calorias);
        delete escolhasAluno[refeicao];
    }

    document.getElementById("total-calorias").querySelector('span').textContent = totalCalorias;
    console.log("Escolhas do Aluno:", escolhasAluno);
    console.log("Total de Calorias:", totalCalorias);
}

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
document.addEventListener("DOMContentLoaded", () => {
    carregarAlunos();

    const listaAlunosSelect = document.getElementById("listaAlunos");
    if (listaAlunosSelect) {
        listaAlunosSelect.addEventListener("change", async function (event) {
            const alunoId = event.target.value;
            if (alunoId) {
                try {
                    const resposta = await fetch(`http://localhost:8080/api/alunos/${alunoId}`);
                    if (!resposta.ok) throw new Error("Erro ao buscar aluno");
                    const aluno = await resposta.json();
                    if (aluno.ultimoImc) {
                        carregarDadosAlimentacao(aluno.ultimoImc, alunoId);
                    } else {
                        console.warn("IMC não disponível para este aluno.");
                        document.getElementById("alimentacao-container").innerHTML = "<p>IMC não disponível para este aluno.</p>";
                    }
                } catch (erro) {
                    console.error("Erro ao buscar dados do aluno:", erro);
                    document.getElementById("alimentacao-container").innerHTML = `<p>Erro ao buscar aluno.</p>`;
                }
            } else {
                document.getElementById("alimentacao-container").innerHTML = "";
                todosAlimentos = {};
                escolhasAluno = {};
                totalCalorias = 0;
                document.getElementById("total-calorias").querySelector('span').textContent = totalCalorias;
                document.getElementById("salvarDieta").style.display = 'none';
            }
        });
    }

    const salvarDietaButton = document.getElementById("salvarDieta");
    if (salvarDietaButton) {
        salvarDietaButton.addEventListener('click', () => {
            const alunoId = document.getElementById("listaAlunos").value;

            if (!alunoId) {
                alert("Por favor, selecione um aluno antes de salvar a dieta.");
                return;
            }

            const dietaData = {
                alimentosPorRefeicao: escolhasAluno
            };

            fetch(`/api/alimentacao/aluno/${alunoId}/salvar-dieta`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dietaData)
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => { throw new Error(`Erro ao salvar a dieta: ${response.status} - ${text}`); });
                }
                return response.text(); // Ou response.json() se o backend retornar JSON
            })
            .then(data => {
                console.log("Dieta salva:", data);
                alert("Dieta salva com sucesso!");
                // Opcional: Limpar as seleções ou redirecionar o usuário
            })
            .catch(error => {
                console.error("Erro ao salvar dieta:", error);
                alert(error.message);
            });
        });
    }
});