#!/usr/bin/env bash
set -euo pipefail

# -------------------------------------------------------------------------
# 1️⃣ CONFIGURATION – edit these values to match your environment
# -------------------------------------------------------------------------
# Base URLs (no trailing slash)
KEYCLOAK_URL="http://localhost:7080"   # <-- change if your KC runs elsewhere
BASE_REALM="master"                    # realm that holds the admin client
TARGET_REALM="glimpse"                 # the realm where you want the new user

# Admin client credentials (must have the `manage-users` role)
ADMIN_CLIENT_ID="admin-cli"            # or any other confidential client
ADMIN_CLIENT_SECRET="aroundly-secret"

# New user details (feel free to param‑ise these as you wish)
NEW_USERNAME="testuser001"
NEW_EMAIL="testuser001@example.com"
NEW_PASSWORD="SecurePass123!"

# -------------------------------------------------------------------------
# 2️⃣ FETCH ADMIN ACCESS TOKEN (client‑credentials flow)
# -------------------------------------------------------------------------

echo "🔐 Getting admin token from realm '${BASE_REALM}'..."

TOKEN_RESPONSE=$(curl -s -X POST "${KEYCLOAK_URL}/realms/${BASE_REALM}/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password" \
  -d "client_id=${ADMIN_CLIENT_ID}" \
  -d "username=admin" \
  -d "password=admin")

ADMIN_TOKEN=$(echo "$TOKEN_RESPONSE" | jq -r '.access_token // empty')
if [[ -z "$ADMIN_TOKEN" ]]; then
  echo "❌ Failed to obtain admin token. Response:"
  echo "$TOKEN_RESPONSE"
  exit 1
fi

echo "✅ Token acquired (length ${#ADMIN_TOKEN} chars)."

# -------------------------------------------------------------------------
# 3️⃣ CREATE THE USER IN THE TARGET REALM
# -------------------------------------------------------------------------
echo "👤 Creating user '${NEW_USERNAME}' in realm '${TARGET_REALM}'..."

CREATE_PAYLOAD=$(cat <<EOF
{
  "username": "${NEW_USERNAME}",
  "email": "${NEW_EMAIL}",
  "enabled": true,
  "credentials": [
    {
      "type": "password",
      "value": "${NEW_PASSWORD}",
      "temporary": false
    }
  ]
}
EOF
)

# Perform the request
CREATE_RESPONSE=$(curl -i -s -X POST "${KEYCLOAK_URL}/admin/realms/${TARGET_REALM}/users" \
  -H "Authorization: Bearer ${ADMIN_TOKEN}" \
  -H "Content-Type: application/json" \
  -d "${CREATE_PAYLOAD}")

# Separate status line / headers from body for easier handling
STATUS_LINE=$(echo "$CREATE_RESPONSE" | head -n1)
HTTP_CODE=$(echo "$STATUS_LINE" | awk '{print $2}')

if [[ "$HTTP_CODE" == "201" ]]; then
  # Success – the Location header contains the new user’s URL (and thus the UUID)
  LOCATION_HEADER=$(echo "$CREATE_RESPONSE" | grep -i '^Location:' | tr -d '\r')
  echo "✅ User created successfully!"
  echo "📍 ${LOCATION_HEADER}"
else
  echo "❌ Failed to create user (HTTP ${HTTP_CODE}). Full response:"
  echo "$CREATE_RESPONSE"
  exit 1
fi

# -------------------------------------------------------------------------
# 4️⃣ DONE
# -------------------------------------------------------------------------

echo "🎉 All done."