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

CREATE TABLE IF NOT EXISTS category
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(64) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP   NOT NULL DEFAULT NOW()
);

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
