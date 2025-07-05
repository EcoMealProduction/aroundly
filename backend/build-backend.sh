#!/bin/bash

echo "🛠️  Building JAR..."
mvn clean package -DskipTests

echo "🐳  Rebuilding Docker image..."
docker build -t backend  .

echo "✔️  Done!"