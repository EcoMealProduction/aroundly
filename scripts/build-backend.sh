#!/usr/bin/env bash

set -Eeuo pipefail

# config
IMAGE_NAME="${IMAGE_NAME:=backend}"
LOG_DIR="${LOG_DIR:=.build-logs}"
DEBUG="${DEBUG:=0}"        # set DEBUG=1 for live logs (no tail on error)
RUN_TESTS="${RUN_TESTS:=1}"# set RUN_TESTS=0 to skip local mvn tests
COMPOSE_UP="${COMPOSE_UP:=1}" # set COMPOSE_UP=0 to skip docker compose up

mkdir -p "$LOG_DIR"

STEP=0
function step() {
  STEP=$((STEP+1))
  echo -e "\n[$STEP] $1"
}

function on_error() {
  local exit_code=$?
  local failed_step="${1:-unknown step}"
  local log_file="${2:-}"

  echo -e "❌  FAILED: $failed_step (exit=$exit_code)"
  if [[ -n "${log_file}" && -f "${log_file}" && "$DEBUG" != "1" ]]; then
    echo -e "\n--- Last 120 log lines (${log_file}) ---"
    tail -n 120 "${log_file}" || true
    echo -e "----------------------------------------"
  fi
  exit "$exit_code"
}

# Run a command capturing logs. On failure, show tail of the log.
# Usage: run "Human readable step" logfile "command ..."
function run() {
  local label="$1"
  local logfile="$2"
  shift 2
  step "$label"
  if [[ "$DEBUG" == "1" ]]; then
    # stream logs live
    { "$@" 2>&1 | tee "${logfile}"; } || on_error "$label" "${logfile}"
  else
    # quiet; show tail on error
    { "$@" > "${logfile}" 2>&1; } || on_error "$label" "${logfile}"
  fi
  echo "✅  OK: $label"
}

# 1. Optional local tests (nice to have; build still happens in Docker)
if [[ "$RUN_TESTS" == "1" ]]; then
  if command -v mvn >/dev/null 2>&1; then
    run "🧪 Running unit tests (local Maven)" "${LOG_DIR}/01-tests.log" \
      mvn -B -e -q test
  else
    step "🧪 Skipping tests (mvn not found). Set RUN_TESTS=0 to hide this."
    echo "ℹ️  Install Maven locally or run with RUN_TESTS=0 to skip."
  fi
fi

# 2. Build Docker image (multi-stage Dockerfile does the Maven build)
run "🐳 Building backend image (${IMAGE_NAME})" "${LOG_DIR}/02-docker-build.log" \
  docker build -t "${IMAGE_NAME}" .

# 3. Optionally bring everything up with docker compose
if [[ "$COMPOSE_UP" == "1" ]]; then
  if [[ -f "docker-compose.yml" || -f "compose.yml" ]]; then
    # Build (in case compose also needs it) and start detached
    run "🔧 docker compose build" "${LOG_DIR}/03-compose-build.log" \
      docker compose build
    run "🚀 docker compose up (detached)" "${LOG_DIR}/04-compose-up.log" \
      docker compose up -d

    # Quick health/info
    step "📊 Service status"
    docker compose ps
    echo "ℹ️  Follow logs: docker compose logs -f --tail=200"
  else
    step "📦 No docker-compose.yml found; skipping compose up."
  fi
fi

echo -e "\n✅ All done!"