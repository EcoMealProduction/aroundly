.PHONY: up down stop clean prune start kill

start:
	chmod +x ./scripts/build-backend.sh
	./scripts/build-backend.sh

# Docker container management

# Start all containers (detached mode)
up:
	docker-compose up -d

# Stop all containers
stop:
	docker-compose stop

# Remove containers and volumes
down:
	docker-compose down -v


# Delete all Docker volumes (dangerous)
prune:
	docker volume prune -f

# Stop containers and remove them along with volumes
clean: stop down
