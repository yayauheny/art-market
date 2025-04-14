--liquibase formatted sql
--changeset Evgeny Leshok:create payment table
CREATE TABLE IF NOT EXISTS payment
(
    id         UUID PRIMARY KEY,
    wallet_id  UUID          NOT NULL REFERENCES wallet (id),
    order_id   UUID          NOT NULL REFERENCES orders (id),
    amount     DECIMAL(8, 2) NOT NULL,
    currency   CHAR(3)       NOT NULL,
    status     VARCHAR(32)   NOT NULL,
    created_at TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP     NOT NULL DEFAULT NOW()
);
--rollback DROP TABLE IF EXISTS payment;
