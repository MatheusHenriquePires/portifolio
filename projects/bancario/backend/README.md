# 🏦 Banco Digital — Backend Spring Boot

API REST para o sistema bancário do portfólio.

## Deploy no Railway (Recomendado — Grátis)

1. Acesse [railway.app](https://railway.app) e crie uma conta (GitHub login)
2. **New Project → Deploy from GitHub repo**
3. Suba este diretório (`backend/`) em um repositório GitHub
4. Railway detecta o `Dockerfile` e faz deploy automático
5. Copie a URL gerada (ex: `https://bancario-xxx.up.railway.app`)

### Configurar o Frontend após deploy

Abra o `index.html` no browser com o parâmetro:
```
index.html?api=https://bancario-xxx.up.railway.app
```
A URL é salva automaticamente no localStorage.

---

## Rodando Localmente

### Com Maven instalado
```bash
mvn spring-boot:run
```

### Sem Maven (script automático)
```bash
cd ..
bash iniciar.sh
```

---

## Endpoints da API

| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/api/accounts` | Listar todas as contas |
| `POST` | `/api/accounts` | Criar conta (`ownerName`, `type`) |
| `GET` | `/api/accounts/{id}` | Detalhes + extrato |
| `POST` | `/api/accounts/{id}/deposit` | Depositar (`amount`) |
| `POST` | `/api/accounts/{id}/withdraw` | Sacar (`amount`) |
| `POST` | `/api/transfer` | Transferir (`fromId`, `toId`, `amount`) |

---

_Desenvolvido por Matheus Pires · Java 21 + Spring Boot 3.2_
