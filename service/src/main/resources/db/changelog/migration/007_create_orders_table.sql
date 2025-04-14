--liquibase formatted sql
--changeset Evgeny Leshok:create orders table
CREATE TABLE IF NOT EXISTS orders
(
    id         UUID PRIMARY KEY,
    user_id    UUID        NOT NULL REFERENCES users (id),
    item_id    UUID        NOT NULL REFERENCES item (id),
    status     VARCHAR(32) NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP   NOT NULL DEFAULT NOW(),
    expire_at  TIMESTAMP   NOT NULL
);
--rollback DROP TABLE IF EXISTS orders;
