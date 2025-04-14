--liquibase formatted sql
--changeset Evgeny Leshok:create bid_hold_balance table
CREATE TABLE IF NOT EXISTS bid_hold_balance
(
    id         UUID PRIMARY KEY,
    wallet_id  UUID          NOT NULL REFERENCES wallet (id),
    auction_id UUID          NOT NULL REFERENCES auction (id),
    bid_id     UUID          NOT NULL REFERENCES bid (id),
    amount     DECIMAL(8, 2) NOT NULL,
    currency   CHAR(3)       NOT NULL,
    status     VARCHAR(32)   NOT NULL,
    created_at TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP     NOT NULL DEFAULT NOW()
);
--rollback DROP TABLE IF EXISTS bid_hold_balance;
