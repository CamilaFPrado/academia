document.addEventListener("DOMContentLoaded", () => {
    carregarAlunos();

    // Adicionar o evento de mudança para o campo de seleção de aluno
    const select = document.getElementById("listaAlunos");
    select.addEventListener("change", async (e) => {
        const alunoId = e.target.value;
        if (alunoId) {
            try {
                const response = await fetch(`http://localhost:8080/api/alunos/${alunoId}`);
                const aluno = await response.json();

                // Preencher os campos de altura e peso
                document.getElementById("altura").value = aluno.altura || '';
                document.getElementById("peso").value = aluno.peso || '';
            } catch (error) {
                console.error("Erro ao buscar dados do aluno:", error);
            }
        }
    });

    const form = document.getElementById("formIMC");
    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const alunoId = document.getElementById("listaAlunos").value;
        const altura = parseFloat(document.getElementById("altura").value);
        const peso = parseFloat(document.getElementById("peso").value);
        const imc = peso / (altura * altura);

        document.getElementById("resultadoIMC").innerText = `IMC: ${imc.toFixed(2)}`;

        // Salvar no backend
        try {
            const response = await fetch(`http://localhost:8080/api/imc/atualizar/${alunoId}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ altura: altura, peso: peso })
            });

            if (!response.ok) throw new Error("Erro ao salvar IMC");

            alert("IMC salvo com sucesso!");
        } catch (error) {
            console.error("Erro ao salvar IMC:", error);
            alert("Erro ao salvar IMC");
        }
    });
});

async function carregarAlunos() {
    const select = document.getElementById("listaAlunos");
    try {
        const response = await fetch("http://localhost:8080/api/alunos");
        const alunos = await response.json();

        select.innerHTML = `<option value="">Selecione um aluno</option>`;
        alunos.forEach(aluno => {
            const option = document.createElement("option");
            option.value = aluno.id;
            option.textContent = aluno.nome;
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao carregar alunos:", error);
        select.innerHTML = `<option value="">Erro ao carregar alunos</option>`;
    }
}
