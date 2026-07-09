#!/usr/bin/env bash
# Esegue seed_rooms.sql contro il container postgres di CleanScheduler avviato via docker compose.
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CONTAINER="${POSTGRES_CONTAINER:-cleanscheduler-postgres-1}"
DB_USER="${POSTGRES_USER:-myuser}"
DB_NAME="${POSTGRES_DB:-mydatabase}"

docker exec -i "$CONTAINER" psql -U "$DB_USER" -d "$DB_NAME" < "$SCRIPT_DIR/seed_rooms.sql"
