--liquibase formatted sql
--changeset Evgeny Leshok:create users table
CREATE TABLE IF NOT EXISTS users
(
    id         UUID PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255),
    role       VARCHAR(32)  NOT NULL,
    address    VARCHAR(255),
    birth_date DATE         NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP    NOT NULL DEFAULT NOW()
);
--rollback DROP TABLE IF EXISTS users;
