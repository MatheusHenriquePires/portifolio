#!/bin/bash
# ─────────────────────────────────────────────────────────────
# deploy.sh — Publica o portfólio no GitHub Pages
# Uso: bash deploy.sh SEU_GITHUB_TOKEN
# ─────────────────────────────────────────────────────────────

TOKEN=$1
USER="MatheusHenriquePires"
REPO="portifolio"

if [ -z "$TOKEN" ]; then
  echo "❌ Passe seu GitHub Personal Access Token:"
  echo "   bash deploy.sh ghp_SEU_TOKEN"
  echo ""
  echo "📝 Como obter o token:"
  echo "   github.com → Settings → Developer settings → Personal Access Tokens → Tokens (classic)"
  echo "   Marque: repo, workflow"
  exit 1
fi

echo "🚀 Criando repositório '$REPO' no GitHub..."

# Criar repositório via API
RESPONSE=$(curl -s -w "\n%{http_code}" \
  -X POST \
  -H "Authorization: token $TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  https://api.github.com/user/repos \
  -d "{\"name\":\"$REPO\",\"description\":\"Portfólio Dev Full Stack — Matheus Pires\",\"homepage\":\"https://$USER.github.io/$REPO\",\"private\":false,\"auto_init\":false}")

HTTP_CODE=$(echo "$RESPONSE" | tail -1)
BODY=$(echo "$RESPONSE" | head -1)

if [ "$HTTP_CODE" = "201" ]; then
  echo "✅ Repositório criado: https://github.com/$USER/$REPO"
elif echo "$BODY" | grep -q "already exists"; then
  echo "ℹ️  Repositório já existe, continuando..."
else
  echo "⚠️  Resposta da API: $HTTP_CODE"
fi

# Adicionar remote e fazer push
echo ""
echo "📤 Publicando no GitHub..."
git remote remove origin 2>/dev/null || true
git remote add origin "https://$TOKEN@github.com/$USER/$REPO.git"
git branch -M main
git push -u origin main --force

if [ $? -eq 0 ]; then
  echo ""
  echo "✅ Portfólio publicado com sucesso!"
  echo ""
  echo "─────────────────────────────────────────────────"
  echo "🌐 Próximo passo — Ativar GitHub Pages:"
  echo "   1. Acesse: https://github.com/$USER/$REPO/settings/pages"
  echo "   2. Em 'Source': selecione 'Deploy from a branch'"
  echo "   3. Branch: 'main' / '/ (root)'"
  echo "   4. Clique em Save"
  echo ""
  echo "🔗 URL do portfólio (em ~1 min):"
  echo "   https://$USER.github.io/$REPO"
  echo "─────────────────────────────────────────────────"
else
  echo "❌ Erro ao fazer push. Verifique o token e tente novamente."
fi
