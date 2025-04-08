document.addEventListener("DOMContentLoaded", async () => {
  const listaContainer = document.getElementById("lista-alunos");

  if (!listaContainer) return;

  try {
    const response = await fetch("http://localhost:8080/api/alunos");
    const alunos = await response.json();

    if (alunos.length === 0) {
      listaContainer.innerHTML = "<p>Nenhum aluno cadastrado.</p>";
      return;
    }

    alunos.forEach(aluno => {
      const card = document.createElement("div");
      card.classList.add("card", "aluno-card");

      card.innerHTML = `
        <h3>${aluno.nome}</h3>
        <p><strong>Email:</strong> ${aluno.email}</p>
        <p><strong>Data de Nascimento:</strong> ${aluno.dataNascimento}</p>
        <p><strong>Altura:</strong> ${aluno.altura} m</p>
        <p><strong>Peso:</strong> ${aluno.peso} kg</p>
        <a class="btn-editar" href="editar-aluno.html?id=${aluno.id}">Editar</a>
      `;

      listaContainer.appendChild(card);
    });
  } catch (error) {
    listaContainer.innerHTML = "<p>Erro ao carregar alunos.</p>";
    console.error("Erro:", error);
  }
});
