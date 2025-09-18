# Aroundly – Stay Connected to What’s Around You

**Aroundly** is a **oldLocation-aware community app** that helps you discover, report, and engage with real-time happenings in your immediate surroundings. From local incidents and hidden gems to spontaneous events or neighborhood tips, Aroundly surfaces what's relevant — right where you are.

Designed for people who want to stay connected to their environment, Aroundly offers:

---

## Features

- 📍 **Location-based discovery** of incidents, events, tips, and local activity
- 🗂️ **Smart filters** based on interest, proximity, and category
- 🧠 **Crowdsourced contributions** from real people, in real time
- 🏆 **Gamified engagement** through badges, points, and leaderboards
- 🧭 A **personalized map interface** that puts you at the center

Whether you’ve just moved to a new city or want to rediscover your own neighborhood, Aroundly helps you explore, connect, and contribute to the world nearby.

---

## Tech Stack

- **Backend:** Java, Spring Boot, Hexagonal Architecture
- **Frontend:** Flutter
- **Database:** PostgreSQL (+ PostGIS for geospatial data)
- **Authentication:** Keycloak
- **Object Storage:** MinIO (S3 compatible)
- **Visualization:** Grafana (optional)
- **CI/CD & Infra:** Docker Compose, GitHub Actions

---
## Configuration

- **Keycloak:** User and roles management (import realm.json on first run)
- **MinIO:** Used for storing all uploaded images and videos
- **PostgreSQL:** Stores all application and geospatial data
- **Environment Variables:** All credentials and secrets are managed via `.env` files (not included in version control)

## Environment Setup
```bash
# 0. Check if you have .env file, if not ask Dan
# 1. Start all required services
docker-compose up -d

# 2. Verify services are running
docker ps

# 3. Build the project
# Linux:
# Give permissions to execute the file:
chmod +x build-backend.sh

# Run
./build-backend.sh
# Windows
# If it’s your first time running a .ps1 script, PowerShell might block it
# Run PowerShell as Administrator and execute:
Set-ExecutionPolicy RemoteSigned
# Then choose [A] Yes to All.
# Run
.\build-backend.ps1

# Run the app
# go from project path to
cd infra/src/main/java/com/backend/infra/
# run the command 
mvn spring-boot:run
# or enter InfraApplication to run the app if you are on Intellij
```
---

## Local Services

| Service          | URL                          | Credentials              |
|------------------|-------------------------------|---------------------------|
| Backend API      | [http://localhost:8100](http://localhost:8100)        | –                         |
| Keycloak Admin   | [http://localhost:7080](http://localhost:7080)        | `admin` / `admin`         |
| PGAdmin          | [http://localhost:15432](http://localhost:15432)      | `admin@pgadmin.com` / `password` |
| MinIO Console    | [http://localhost:6001](http://localhost:6001)        | `admin` / `password`      |
| Grafana          | [http://localhost:3000](http://localhost:3000)        | `admin` / `password`      |


## Docker Commands
```
# Start all services
docker-compose up -d

# Stop all services
docker-compose down

# Rebuild and start
docker-compose up --build -d

# View logs
docker-compose logs -f [service_name]

# Remove volumes (reset data)
docker-compose down -v
```

## License

This project is licensed under the [MIT License](LICENSE).

---

## Authors
- Ecomeal

---
