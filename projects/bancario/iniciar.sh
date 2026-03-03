#!/bin/bash
# ─────────────────────────────────────────────────
# Iniciar o Banco Digital — Backend Spring Boot
# Baixa Maven automaticamente se não estiver instalado
# ─────────────────────────────────────────────────

set -e
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
BACKEND_DIR="$SCRIPT_DIR/backend"
MVN_DIR="$SCRIPT_DIR/.mvn-bin"

# Verificar Java
if ! command -v java &>/dev/null; then
  echo "❌ Java não encontrado. Instale Java 17+ e tente novamente."
  exit 1
fi

JAVA_VER=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d. -f1)
echo "✅ Java $JAVA_VER encontrado"

# Verificar/baixar Maven
if command -v mvn &>/dev/null; then
  MVN_CMD="mvn"
  echo "✅ Maven encontrado: $(mvn --version | head -1)"
else
  echo "📦 Maven não encontrado. Baixando Maven 3.9.6..."
  mkdir -p "$MVN_DIR"
  MVN_TAR="$MVN_DIR/maven.tar.gz"
  MVN_HOME="$MVN_DIR/apache-maven-3.9.6"
  if [ ! -d "$MVN_HOME" ]; then
    curl -fsSL "https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz" -o "$MVN_TAR"
    tar -xzf "$MVN_TAR" -C "$MVN_DIR"
    rm "$MVN_TAR"
    echo "✅ Maven 3.9.6 baixado em $MVN_HOME"
  fi
  MVN_CMD="$MVN_HOME/bin/mvn"
fi

# Iniciar projeto
echo ""
echo "🚀 Iniciando Banco Digital na porta 8080..."
echo "   Acesse o frontend: abra projects/bancario/index.html no browser"
echo "   API disponível em: http://localhost:8080/api/accounts"
echo "   Pressione Ctrl+C para parar"
echo ""

cd "$BACKEND_DIR"
"$MVN_CMD" spring-boot:run -q
