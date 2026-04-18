--liquibase formatted sql
--changeset nikolay:3
CREATE TABLE IF NOT EXISTS  users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'USER'
);

INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuMYyJDdXT9MNoIP5Z6vGU6.S6Mq6I4.', 'ADMIN');
--rollback DROP TABLE users;