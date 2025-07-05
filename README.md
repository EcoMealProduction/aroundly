# City Safe

**City Safe** is a modern platform designed to help communities report, track, and respond to urban incidents and events. By combining geolocation, real-time updates, and community feedback, City Safe empowers citizens to make their cities safer and more connected.

---

## Features

- 📍 **Location-based reporting** of incidents and events
- 🔒 **Secure authentication** using Keycloak
- 🖼️ **Image and video uploads** via MinIO object storage
- 🗺️ **Interactive maps** for visualizing posts and incidents
- 💬 **Community feedback** (comments, likes, confirms/denies)
- 📊 **Admin dashboards** and analytics (Grafana ready)
- 🔔 **Notifications** for relevant events nearby

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

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Authors
- Ecomeal

---
