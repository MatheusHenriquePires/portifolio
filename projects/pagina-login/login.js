// Pega os elementos corretamente
const loginform = document.getElementById('loginForm');
const nomeInput = document.getElementById('nome');
const emailInput = document.getElementById('email');
const urlInput = document.getElementById('url');
const feedbackParagrafo = document.getElementById('feedback');

loginform.addEventListener('submit', (evento) => {
    evento.preventDefault();

    const nome = nomeInput.value.trim();
    const email = emailInput.value.trim();
    const url = urlInput.value.trim();

    try {
        // Salva no localStorage
        localStorage.setItem('usuario_nome', nome);
        localStorage.setItem('usuario_email', email);
        localStorage.setItem('usuario_url', url);

        feedbackParagrafo.textContent = 'Dados salvos com sucesso!';
        feedbackParagrafo.style.color = 'green';

        console.log('Dados salvos no localStorage:', {
            nome: localStorage.getItem('usuario_nome'),
            email: localStorage.getItem('usuario_email'),
            url: localStorage.getItem('usuario_url')
        });
    } catch (e) {
        feedbackParagrafo.textContent = 'Não foi possível salvar os dados.';
        feedbackParagrafo.style.color = 'red';
        console.error('Erro ao salvar no localStorage:', e);
    }
});
