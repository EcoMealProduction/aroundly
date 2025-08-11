# Development Information Dictionary

## 🚀 Quick Start Guide

### Environment Setup
```bash
# 1. IMPORTANT: Start Docker containers FIRST
docker-compose up -d

# 2. Verify services are running (should see keycloak_db and app_db containers)
docker ps

# 3. Build and test the application
mvn clean package
mvn test

# 4. Start the Spring Boot application (from /backend directory)
cd infra
mvn spring-boot:run
```

### ⚠️ Critical Startup Order
**Docker containers MUST be started before the Spring Boot application!**
The app will immediately try to connect to databases on startup and fail if containers aren't running.

### Service Access URLs
- **Backend API**: http://localhost:8100
- **Keycloak Admin**: http://localhost:7080 (admin/admin)
- **PGAdmin**: http://localhost:15432 (admin@pgadmin.com/password)
- **MinIO Console**: http://localhost:6001 (admin/password)
- **Grafana**: http://localhost:3000 (admin/password)

## 🏗️ Build & Development Commands

### Maven Commands
```bash
# Clean build
mvn clean package

# Skip tests during build
mvn clean package -DskipTests

# Run only tests
mvn test

# Generate code coverage report
mvn test jacoco:report

# Run specific test class
mvn test -Dtest=ClassName

# Build with Docker
./build-backend.sh
```

### Docker Commands
```bash
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

## 📁 Project Structure Guide

### Module Responsibilities
- **infra/**: Spring Boot main application, security configuration, entry point
- **domain/**: Core business entities (Event, Incident, Location, Comment)
- **port/**: Use cases and repository interfaces (hexagonal architecture)
- **application/**: Business logic services and strategies
- **adapter/**: External adapters (REST controllers, DTOs, mappers)
- **code-coverage/**: JaCoCo coverage reports

### Key Files & Locations
- Main application: `infra/src/main/java/com/backend/infra/InfraApplication.java`
- Security config: `infra/src/main/java/com/backend/infra/security/`
- Domain models: `domain/src/main/java/com/backend/happening/`
- REST endpoints: `adapter/src/main/java/com/backend/in/rest/`
- DTOs: `adapter/src/main/java/com/backend/in/dto/`
- Mappers: `adapter/src/main/java/com/backend/in/mapper/`

## 🔧 Configuration Files

### Environment Variables (.env)
- Database: POSTGRES_DB, POSTGRES_USER, POSTGRES_PASSWORD
- Keycloak: KEYCLOAK_ADMIN, KEYCLOAK_ADMIN_PASSWORD
- MinIO: MINIO_ROOT_USER, MINIO_ROOT_PASSWORD
- Grafana: GF_SECURITY_ADMIN_USER, GF_SECURITY_ADMIN_PASSWORD

### Application Properties
- **Root**: Basic Spring app name configuration
- **Infra module**: OAuth2/Keycloak configuration, server port (8100)

### Docker Configuration
- **docker-compose.yml**: Service definitions and port mappings
- **Dockerfile**: Application container build instructions

## 🧪 Testing Strategy

### Test Types
- **Unit Tests**: Each module has tests in `src/test/java/`
- **Integration Tests**: Security integration tests in infra module
- **Test Fixtures**: Shared test data in `Fixtures.java` files

### Coverage Reports
- Run: `mvn test jacoco:report`
- Location: `target/site/jacoco/index.html` in each module
- Aggregate: `code-coverage/target/site/jacoco-aggregate/index.html`

## 🔐 Security & Authentication

### Keycloak Setup
- Realm: glimpse
- Client ID: aroundly
- Client Secret: aroundly-secret
- Admin Console: http://localhost:7080
- Realm config: `infra/src/main/resources/keycloak/realm.json`

### Authentication Flow
1. OAuth2 authorization code flow
2. JWT token processing via JwtAuthConverter
3. Spring Security configuration in SpringSecurity.java

## 🗄️ Database Information

### Separate Database Setup
The project uses **two separate PostgreSQL databases** to isolate application data from authentication data:

#### Keycloak Database (Authentication)
- **Host**: localhost:5432
- **Database**: users
- **User**: shogun
- **Password**: password
- **Purpose**: Keycloak authentication tables (~60+ tables)

#### Application Database (Business Logic)
- **Host**: localhost:5433 
- **Database**: appdb
- **User**: appuser
- **Password**: app_password
- **Purpose**: Application tables (incidents, events, etc.)

### For WSL Users (DBeaver Connection)
Use your WSL IP instead of localhost. Find it with:
```bash
ip route show | grep -i default | awk '{ print $3}' | head -1
```
Then connect using that IP (e.g., `172.28.112.1:5433`)

### Management Tools
- **PGAdmin**: http://localhost:15432
- **Direct connection**: Use above credentials with any PostgreSQL client

### Database Migration (Flyway)
- **Status**: Enabled in application.properties but **dependencies removed from pom.xml**
- **Migration Location**: `adapter/src/main/resources/db/migration/` 
- **Current Migration**: `V1__create_incidents_table.sql` (creates incidents table)
- **⚠️ Important**: Flyway dependencies need to be re-added to infra/pom.xml for automatic migrations

### Database Versions  
- **PostgreSQL**: 14.13 (via `postgis/postgis:14-3.4`)
- **Compatibility**: PostgreSQL 14.x works with most Flyway versions

## 📊 Monitoring & Observability

### Available Tools
- **Grafana**: http://localhost:3000 for dashboards and monitoring
- **JaCoCo**: Code coverage reports
- **Spring Boot Actuator**: Health endpoints (if enabled)

## 🔄 Development Workflow

### Feature Development
1. Create feature branch from main
2. Implement changes following hexagonal architecture
3. Add/update tests (unit + integration)
4. Run full test suite: `mvn test`
5. Check code coverage: `mvn jacoco:report`
6. Build and test with Docker: `./build-backend.sh`

### Code Style & Patterns
- **Lombok**: Reduce boilerplate (@Data, @Builder, @AllArgsConstructor)
- **MapStruct**: Entity-DTO mapping (see existing mappers)
- **Strategy Pattern**: For different happening types (Event/Incident)
- **Hexagonal Architecture**: Clear separation of ports and adapters

## 🚨 Troubleshooting

### Common Issues

#### 1. Database Connection Refused (Most Common)
**Error**: `Connection to localhost:5433 refused`  
**Cause**: Docker containers are not running  
**Solution**:
```bash
# Check if containers are running
docker ps

# If not running, start them  
docker-compose up -d

# Verify both databases are up
docker ps | grep -E "(keycloak_db|app_db)"
```

#### 2. Flyway Migration Issues
**Error**: Various Flyway-related errors  
**Cause**: Flyway dependencies removed from infra/pom.xml  
**Solution**: Add back Flyway dependencies or disable in application.properties

#### 3. Port Conflicts
Check if ports 8100, 5432, 5433, 7080, etc. are available

#### 4. Docker Issues
Try complete reset: `docker-compose down -v && docker-compose up -d`

#### 5. Database Connection Issues
Verify PostgreSQL containers are running and healthy

#### 6. Keycloak Realm Issues
Check if realm.json is properly imported

#### 7. Memory Issues
Increase Docker memory allocation in Docker Desktop

### Useful Commands
```bash
# Check running containers
docker ps

# Check application logs
docker-compose logs backend

# Check Keycloak database connection
docker exec -it keycloak_db psql -U shogun -d users

# Check application database connection  
docker exec -it app_db psql -U appuser -d appdb

# Restart specific service
docker-compose restart keycloak

# Clean Maven cache
mvn clean
```