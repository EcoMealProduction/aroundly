-- Client table (users from Keycloak)
CREATE TABLE client (
    keycloak_id VARCHAR PRIMARY KEY,
    username VARCHAR NOT NULL,
    avatar_url VARCHAR,
    fcm_token VARCHAR
);

-- Location table for GPS coordinates and addresses
CREATE TABLE location (
    id SERIAL PRIMARY KEY,
    lat DOUBLE PRECISION NOT NULL,
    long DOUBLE PRECISION NOT NULL,
    address_text VARCHAR
);

-- Happening table (base for incidents and events)
CREATE TABLE happening (
    id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    client_id VARCHAR NOT NULL REFERENCES client(keycloak_id),
    location_id INT NOT NULL REFERENCES location(id),
    media_url VARCHAR,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Incidents table linked to happenings
CREATE TABLE incident (
    id SERIAL PRIMARY KEY,
    happening_id INT NOT NULL REFERENCES happening(id),
    expires_at TIMESTAMP NOT NULL,
    confirms INT DEFAULT 0,
    denies INT DEFAULT 0
);
