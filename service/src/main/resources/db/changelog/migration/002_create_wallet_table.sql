--liquibase formatted sql
--changeset Evgeny Leshok:create wallet table
CREATE TABLE IF NOT EXISTS wallet
(
    id           UUID PRIMARY KEY,
    owner_id     UUID           NOT NULL UNIQUE REFERENCES users (id),
    currency     CHAR(3)        NOT NULL,
    balance      DECIMAL(10, 2) NOT NULL,
    payment_info VARCHAR(255)   NOT NULL,
    created_at   TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP      NOT NULL DEFAULT NOW()
);
--rollback DROP TABLE IF EXISTS wallet;
