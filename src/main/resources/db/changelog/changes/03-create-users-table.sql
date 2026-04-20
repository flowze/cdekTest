--liquibase formatted sql
--changeset nikolay:3
CREATE TABLE IF NOT EXISTS  users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'USER'
);

INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$12$RnEfvS4lf6P8Jczvvq6rF.YWQ0JxBjvKDAm1m0DofOGNBiJWmDCma', 'ADMIN');
--rollback DROP TABLE users;