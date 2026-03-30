#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../../.." && pwd)"
COLLECTION_FILE="$REPO_ROOT/tests/api/postman/ecomada-collect.postman_collection.json"

DEFAULT_REMOTE_URL="https://ecomada-collect-c8395a62a36e.herokuapp.com"
DEFAULT_LOCAL_URL="http://localhost:8090"

TARGET="${1:-remote}"

case "$TARGET" in
  local)
    BASE_URL="$DEFAULT_LOCAL_URL"
    ;;
  remote)
    BASE_URL="$DEFAULT_REMOTE_URL"
    ;;
  http://*|https://*)
    BASE_URL="$TARGET"
    ;;
  *)
    echo "Usage: $0 [remote|local|https://custom-url]"
    exit 1
    ;;
esac

if command -v newman >/dev/null 2>&1; then
  NEWMAN_CMD=(newman)
elif command -v npx >/dev/null 2>&1; then
  NEWMAN_CMD=(npx --yes newman)
else
  echo "Erreur: newman introuvable. Installez-le avec: npm install -g newman"
  exit 1
fi

echo "Execution Newman sur: $BASE_URL"
echo "Collection: $COLLECTION_FILE"

"${NEWMAN_CMD[@]}" run "$COLLECTION_FILE" \
  --env-var "base_url=$BASE_URL" \
  --reporters cli
