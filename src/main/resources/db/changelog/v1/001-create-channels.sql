--liquibase formatted sql

--changeset veteroch4k:001-create-channels
CREATE TABLE channels (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    title VARCHAR(255),
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    last_message_id BIGINT,
    last_fetched_at TIMESTAMPTZ,
    consecutive_errors INT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT uq_channels_username UNIQUE (username),
    CONSTRAINT chk_channels_username_norm CHECK (username = lower(username) AND username ~ '^[a-z0-9_]{5,32}$'),
    CONSTRAINT chk_channels_status CHECK (status IN ('ACTIVE', 'PAUSED', 'INVALID')),
    CONSTRAINT chk_channels_errors CHECK (consecutive_errors >= 0)
);

CREATE INDEX idx_channels_active_fetch
    ON channels (last_fetched_at NULLS first)
    WHERE status = 'ACTIVE';

--rollback DROP TABLE IF EXISTS channels;