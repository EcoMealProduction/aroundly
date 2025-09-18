#!/usr/bin/env bash
set -euo pipefail

# -------------------------------------------------------------------------
# 1️⃣ SETTINGS – change to match your environment
# -------------------------------------------------------------------------
KEYCLOAK_URL="http://localhost:7080"   # Base URL of your Keycloak instance
REALM="glimpse"                        # Realm where the user lives
CLIENT_ID="aroundly"                   # Confidential client that allows direct‑grant
CLIENT_SECRET="aroundly-secret"        # Its secret (must be set in the client config)

# The user you want to log in (must already exist)
USERNAME="testuser001"
PASSWORD="SecurePass123!"
# -------------------------------------------------------------------------

echo "🔐 Requesting token for user '${USERNAME}' in realm '${REALM}'..."

TOKEN_RESPONSE=$(curl -s -X POST "${KEYCLOAK_URL}/realms/${REALM}/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password" \
  -d "client_id=${CLIENT_ID}" \
  -d "username=${USERNAME}" \
  -d "password=${PASSWORD}" \
  $( [[ -n "${CLIENT_SECRET}" ]] && echo "-d client_secret=${CLIENT_SECRET}" ))

# Pretty‑print the raw JSON (you can also pipe through `jq .` if you like)
echo "🗂️ Raw token response:"
echo "$TOKEN_RESPONSE" | jq .

# -------------------------------------------------------------------------
# 3️⃣ Extract useful fields (optional – handy for later API calls)
# -------------------------------------------------------------------------
ACCESS_TOKEN=$(echo "$TOKEN_RESPONSE" | jq -r '.access_token // empty')
REFRESH_TOKEN=$(echo "$TOKEN_RESPONSE" | jq -r '.refresh_token // empty')
ID_TOKEN=$(echo "$TOKEN_RESPONSE" | jq -r '.id_token // empty')
EXPIRES_IN=$(echo "$TOKEN_RESPONSE" | jq -r '.expires_in // empty')

if [[ -z "$ACCESS_TOKEN" ]]; then
  echo "❌ Failed to obtain an access token. Check the response above for errors."
  exit 1
fi

echo "✅ Got access token (expires in ${EXPIRES_IN}s)."

