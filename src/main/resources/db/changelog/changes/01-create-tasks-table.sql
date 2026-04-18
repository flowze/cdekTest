--liquibase formatted sql
--changeset nikolay:1
CREATE TABLE IF NOT EXISTS  tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'NEW'
);
--rollback DROP TABLE tasks;