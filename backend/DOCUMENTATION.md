# How to Start the Application

## Prerequisites
- Java 21
- Maven 3.x
- Docker and Docker Compose

## Quick Start

1. **Start Docker services**
   ```bash
   docker-compose up -d
   ```

2. **Build the application**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   cd infra && mvn spring-boot:run
   ```

## Service URLs
- **Backend API**: http://localhost:8100
- **Keycloak Admin**: http://localhost:7080 (Login: KEYCLOAK_ADMIN / KEYCLOAK_ADMIN_PASSWORD)
- **PGAdmin**: http://localhost:15432 (Login: PGADMIN_DEFAULT_EMAIL / PGADMIN_DEFAULT_PASSWORD)
- **MinIO Console**: http://localhost:6001 (Login: MINIO_ROOT_USER / MINIO_ROOT_PASSWORD)
- **Grafana**: http://localhost:3000 (Login: GF_SECURITY_ADMIN_USER / GF_SECURITY_ADMIN_PASSWORD)

## Database Configuration
- **Database**: PostgreSQL with PostGIS
- **Host**: localhost:POSTGRES_PORT
- **Database Name**: POSTGRES_DB
- **Username**: POSTGRES_USER
- **Password**: POSTGRES_PASSWORD
- **Schema Management**: Hibernate auto-update (creates/updates tables automatically)

## Authentication
- **OAuth2 Provider**: Keycloak
- **Realm**: ecomeal
- **Client ID**: spring.security.oauth2.client.registration.keycloak.client-id
- **Authorization Flow**: Authorization Code with PKCE

## Troubleshooting
- Verify all Docker containers are running: `docker ps`
- Check application logs: `docker-compose logs -f`
- Check database connection: `docker exec -it db psql -U POSTGRES_USER -d POSTGRES_DB`
- If ports are in use, stop conflicting services first
- Tables are created automatically by Hibernate on first run