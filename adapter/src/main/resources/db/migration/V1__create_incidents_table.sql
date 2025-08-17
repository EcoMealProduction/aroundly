CREATE TABLE incidents (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    range INT,
    confirms INT,
    denies INT
);
