const loginForm = document.getElementById('loginForm');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
const rememberInput = document.getElementById('remember');
const togglePasswordBtn = document.getElementById('togglePassword');
const submitBtn = document.getElementById('submitBtn');
const feedback = document.getElementById('feedback');

const loginCard = document.getElementById('login-card');
const sessionCard = document.getElementById('sessionCard');
const sessionInfo = document.getElementById('sessionInfo');
const logoutBtn = document.getElementById('logoutBtn');

const SESSION_KEY = 'mychat_session';
const REMEMBER_KEY = 'mychat_remember_email';

function setFeedback(message, type) {
    feedback.textContent = message;
    feedback.className = `feedback ${type}`;
}

function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function showSession(session) {
    loginCard.classList.add('hidden');
    sessionCard.classList.remove('hidden');
    sessionInfo.textContent = `Usuário: ${session.email} | Login em: ${new Date(session.timestamp).toLocaleString('pt-BR')}`;
}

function showLogin() {
    sessionCard.classList.add('hidden');
    loginCard.classList.remove('hidden');
}

function loadRememberedEmail() {
    const remembered = localStorage.getItem(REMEMBER_KEY);
    if (remembered) {
        emailInput.value = remembered;
        rememberInput.checked = true;
    }
}

function loadSession() {
    try {
        const raw = localStorage.getItem(SESSION_KEY);
        if (!raw) return;
        const session = JSON.parse(raw);
        if (!session?.email || !session?.timestamp) return;
        showSession(session);
    } catch (_) {
        localStorage.removeItem(SESSION_KEY);
    }
}

async function handleLogin(event) {
    event.preventDefault();
    setFeedback('', '');

    const email = emailInput.value.trim().toLowerCase();
    const password = passwordInput.value;

    if (!isValidEmail(email)) {
        setFeedback('Digite um e-mail valido.', 'error');
        return;
    }

    if (password.length < 6) {
        setFeedback('A senha deve ter pelo menos 6 caracteres.', 'error');
        return;
    }

    submitBtn.disabled = true;
    submitBtn.textContent = 'Entrando...';

    await new Promise(resolve => setTimeout(resolve, 700));

    const session = {
        email,
        timestamp: Date.now()
    };

    localStorage.setItem(SESSION_KEY, JSON.stringify(session));

    if (rememberInput.checked) {
        localStorage.setItem(REMEMBER_KEY, email);
    } else {
        localStorage.removeItem(REMEMBER_KEY);
    }

    setFeedback('Login realizado com sucesso.', 'success');
    showSession(session);

    submitBtn.disabled = false;
    submitBtn.textContent = 'Entrar';
}

function handleLogout() {
    localStorage.removeItem(SESSION_KEY);
    passwordInput.value = '';
    showLogin();
    setFeedback('Sessao encerrada.', 'success');
}

togglePasswordBtn.addEventListener('click', () => {
    const isPassword = passwordInput.type === 'password';
    passwordInput.type = isPassword ? 'text' : 'password';
    togglePasswordBtn.textContent = isPassword ? '🙈' : '👁';
});

loginForm.addEventListener('submit', handleLogin);
logoutBtn.addEventListener('click', handleLogout);

loadRememberedEmail();
loadSession();
