--liquibase formatted sql
--changeset Evgeny Leshok:create auction table
CREATE TABLE IF NOT EXISTS auction
(
    id                UUID PRIMARY KEY,
    item_id           UUID          NOT NULL REFERENCES item (id),
    initial_bid_price DECIMAL(8, 2) NOT NULL,
    min_bid_step      DECIMAL(8, 2) NOT NULL,
    status            VARCHAR(32)   NOT NULL,
    created_at        TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP     NOT NULL DEFAULT NOW(),
    finished_at       TIMESTAMP
);
--rollback DROP TABLE IF EXISTS auction;
