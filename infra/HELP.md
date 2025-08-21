# infra Module

This module contains all **infrastructure-related code and configuration** for the application, following the Hexagonal (Ports & Adapters) Architecture pattern.

## Purpose

The `infra` module is responsible for integrating the application with external systems and technical concerns, such as:

- **Database adapters** (repositories, JPA implementations)
- **Messaging adapters** (Kafka, RabbitMQ, etc.)
- **REST API clients** and external service integrations
- **File storage** (MinIO, S3, etc.)
- **Security and authentication** integrations
- **Technical configuration** (e.g., Spring configuration classes)

## Usage

- This module is intended to be **imported and used by the application core and other modules**.
- All technical adapters live here, with **no domain/business logic**.
