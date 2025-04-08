document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('formCadastro')) {
        configurarCadastro();
    }
});

async function configurarCadastro() {
    const form = document.getElementById('formCadastro');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const aluno = {
            nome: form.nome.value,
            email: form.email.value,
            senha: form.senha.value,
            dataNascimento: form.dataNascimento.value,
            altura: parseFloat(form.altura.value),
            peso: parseFloat(form.peso.value),
            dataCadastro: new Date().toISOString().split("T")[0]
        };

        try {
            const response = await fetch('http://localhost:8080/api/alunos', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(aluno)
            });

            if (response.ok) {
                const alunoCriado = await response.json();

                // Salvando ID e IMC no localStorage
                localStorage.setItem('alunoId', alunoCriado.id);
                localStorage.setItem('imc', alunoCriado.ultimoImc);

                alert('Aluno cadastrado com sucesso!');
                window.location.href = 'index.html';
            } else {
                alert('Erro ao cadastrar aluno.');
            }
        } catch (error) {
            console.error('Erro:', error);
            alert('Erro ao conectar com o servidor.');
        }
    });
}
