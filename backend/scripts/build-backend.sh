#!/bin/bash

set -e  # Exit immediately if any command fails

STEPS=5
CURRENT_STEP=1

function show_progress() {
  echo ""
  echo "[$CURRENT_STEP/$STEPS] $1"
  ((CURRENT_STEP++))
}

show_progress "🧪 Running tests..."
mvn test

show_progress "🛠️  Building JAR..."
mvn clean package -DskipTests

show_progress "📦 Checking for Dockerfile..."
if [ ! -f Dockerfile ]; then
  echo "⚠️  Dockerfile not found. Creating default Dockerfile..."
  cat <<EOF > Dockerfile
FROM openjdk:21-jdk-slim
LABEL authors="ecomeal-production"
COPY infra/target/infra-0.0.1.jar app/app.jar

ENTRYPOINT ["java", "-jar", "app/app.jar"]

EOF
fi

show_progress "🐳 Building Docker image..."
docker build -t backend .

show_progress "✔️ Done!"