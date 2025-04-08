document.addEventListener('DOMContentLoaded', carregarProgresso);

async function carregarProgresso() {
    const container = document.getElementById('progresso-alunos');

    try {
        const response = await fetch('http://localhost:8080/api/progressos');
        const dados = await response.json();

        if (dados.length === 0) {
            container.innerHTML = "<p>Nenhum progresso registrado ainda.</p>";
            return;
        }

        const ul = document.createElement('ul');
        dados.forEach(item => {
            const li = document.createElement('li');
            li.innerHTML = `
                <strong>${item.nomeAluno}</strong><br>
                Peso: ${item.peso} kg | Altura: ${item.altura} m | IMC: ${item.imc.toFixed(2)}<br>
                Data: ${item.data}
                <hr>
            `;
            ul.appendChild(li);
        });

        container.appendChild(ul);
    } catch (error) {
        console.error('Erro ao carregar progresso:', error);
        container.innerHTML = "<p>Erro ao carregar os dados de progresso.</p>";
    }
}
