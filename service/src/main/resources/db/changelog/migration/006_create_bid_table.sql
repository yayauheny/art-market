--liquibase formatted sql
--changeset Evgeny Leshok:create bid table
CREATE TABLE IF NOT EXISTS bid
(
    id         UUID PRIMARY KEY,
    auction_id UUID          NOT NULL REFERENCES auction (id),
    user_id    UUID          NOT NULL REFERENCES users (id),
    status     VARCHAR(32)   NOT NULL,
    price      DECIMAL(8, 2) NOT NULL,
    created_at TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP     NOT NULL DEFAULT NOW()
);
--rollback DROP TABLE IF EXISTS bid;
