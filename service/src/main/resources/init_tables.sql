CREATE TABLE user
(
    id         UUID PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255),
    role       VARCHAR(32)  NOT NULL,
    address    VARCHAR(255),
    birth_date DATE         NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP    NOT NULL
);

CREATE TABLE wallet
(
    id           UUID PRIMARY KEY,
    owner_id     UUID           NOT NULL UNIQUE,
    currency     VARCHAR(255)   NOT NULL,
    balance      DECIMAL(10, 2) NOT NULL,
    payment_info VARCHAR(255)   NOT NULL,
    created_at   TIMESTAMP      NOT NULL,
    updated_at   TIMESTAMP      NOT NULL,

    CONSTRAINT wallet_owner_id_fk
        FOREIGN KEY (owner_id)
            REFERENCES user (id)
);

CREATE TABLE category
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(64) NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP   NOT NULL,
    updated_at  TIMESTAMP   NOT NULL
);

CREATE TABLE item
(
    id            UUID PRIMARY KEY,
    category_id   UUID          NOT NULL,
    seller_id     UUID          NOT NULL,
    purchase_type VARCHAR(255)  NOT NULL,
    name          VARCHAR(255)  NOT NULL,
    description   VARCHAR(255),
    condition     VARCHAR(255)  NOT NULL,
    status        VARCHAR(255)  NOT NULL,
    price         DECIMAL(8, 2) NOT NULL,
    currency      VARCHAR(255)  NOT NULL,
    image_path    VARCHAR(255),
    version       SMALLINT      NOT NULL,
    created_at    TIMESTAMP     NOT NULL,
    updated_at    TIMESTAMP     NOT NULL,

    CONSTRAINT item_category_id_fk
        FOREIGN KEY (category_id)
            REFERENCES category (id),
    CONSTRAINT item_seller_id_fk
        FOREIGN KEY (seller_id)
            REFERENCES user (id)
);

CREATE TABLE auction
(
    id             UUID PRIMARY KEY,
    seller_id      UUID          NOT NULL,
    item_id        UUID          NOT NULL,
    bid_id         UUID          NOT NULL,
    starting_price DECIMAL(8, 2) NOT NULL,
    min_step       DECIMAL(8, 2) NOT NULL,
    status         VARCHAR(255)  NOT NULL,
    duration       BIGINT        NOT NULL,
    created_at     TIMESTAMP(0)  NOT NULL,
    updated_at     TIMESTAMP(0)  NOT NULL,
    finished_at    TIMESTAMP(0)  NOT NULL,

    CONSTRAINT auction_item_id_fk
        FOREIGN KEY (item_id)
            REFERENCES item (id),
    CONSTRAINT auction_seller_id_fk
        FOREIGN KEY (seller_id)
            REFERENCES user (id),
    CONSTRAINT auction_bid_id_fk
        FOREIGN KEY (bid_id)
            REFERENCES bid (id)
);

CREATE TABLE bid
(
    id         UUID PRIMARY KEY,
    auction_id UUID          NOT NULL,
    user_id    UUID          NOT NULL,
    status     VARCHAR(255)  NOT NULL,
    price      DECIMAL(8, 2) NOT NULL,
    created_at TIMESTAMP(0)  NOT NULL,
    updated_at TIMESTAMP(0)  NOT NULL,

    CONSTRAINT bid_user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES user (id),
    CONSTRAINT bid_auction_id_fk
        FOREIGN KEY (auction_id)
            REFERENCES auction (id)
);

CREATE TABLE payment
(
    id         UUID PRIMARY KEY,
    wallet_id  UUID          NOT NULL,
    amount     DECIMAL(8, 2) NOT NULL,
    currency   VARCHAR(255)  NOT NULL,
    type       VARCHAR(255)  NOT NULL,
    status     VARCHAR(255)  NOT NULL,
    created_at TIMESTAMP(0)  NOT NULL,
    updated_at TIMESTAMP(0)  NOT NULL,

    CONSTRAINT payment_wallet_id_fk
        FOREIGN KEY (wallet_id)
            REFERENCES wallet (id)
);

CREATE TABLE order
(
    id         UUID PRIMARY KEY,
    user_id    UUID         NOT NULL,
    payment_id UUID         NOT NULL UNIQUE,
    item_id    UUID         NOT NULL,
    status     VARCHAR(255) NOT NULL CHECK (status IN ('pending', 'paid', 'canceled')),
    created_at TIMESTAMP(0) NOT NULL,
    updated_at TIMESTAMP(0) NOT NULL,
    expire_at  TIMESTAMP(0) NOT NULL,

    CONSTRAINT order_user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES user (id),
    CONSTRAINT order_item_id_fk
        FOREIGN KEY (item_id)
            REFERENCES item (id),
    CONSTRAINT order_payment_id_fk
        FOREIGN KEY (payment_id)
            REFERENCES payment (id)
);

CREATE TABLE bid_hold
(
    id         UUID PRIMARY KEY,
    wallet_id  UUID          NOT NULL,
    auction_id UUID          NOT NULL,
    bid_id     UUID          NOT NULL,
    amount     DECIMAL(8, 2) NOT NULL,
    currency   VARCHAR(255)  NOT NULL,
    status     VARCHAR(255)  NOT NULL,
    created_at TIMESTAMP     NOT NULL,
    updated_at TIMESTAMP     NOT NULL,

    CONSTRAINT bid_hold_wallet_id_fk
        FOREIGN KEY (wallet_id)
            REFERENCES wallet (id),
    CONSTRAINT bid_hold_auction_id_fk
        FOREIGN KEY (auction_id)
            REFERENCES auction (id)
);
