--liquibase formatted sql
--changeset Evgeny Leshok:create item table
CREATE TABLE IF NOT EXISTS item
(
    id           UUID PRIMARY KEY,
    category_id  UUID           NOT NULL REFERENCES category (id),
    seller_id    UUID           NOT NULL REFERENCES users (id),
    name         VARCHAR(255)   NOT NULL,
    description  VARCHAR(255),
    condition    VARCHAR(32)    NOT NULL,
    payment_type VARCHAR(32)    NOT NULL,
    status       VARCHAR(32)    NOT NULL,
    price        DECIMAL(10, 2) NOT NULL,
    currency     CHAR(3)        NOT NULL,
    media_path   VARCHAR(255),
    version      INT            NOT NULL,
    created_at   TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP      NOT NULL DEFAULT NOW()
);
--rollback DROP TABLE IF EXISTS item;
