--liquibase formatted sql

--changeset veteroch4k:002-create-posts
CREATE TABLE posts (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    channel_id BIGINT NOT NULL REFERENCES channels(id) ON DELETE CASCADE,
    external_message_id BIGINT NOT NULL,
    text TEXT NOT NULL,
    links JSONB NOT NULL DEFAULT '[]'::jsonb,
    content_hash VARCHAR(64),
    published_at TIMESTAMPTZ NOT NULL,
    fetched_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT uq_posts_channel_message UNIQUE (channel_id, external_message_id)
);

CREATE INDEX idx_posts_published_at
    ON posts (published_at DESC);

CREATE INDEX idx_posts_content_hash
    ON posts (content_hash)
    WHERE content_hash IS NOT NULL;

--rollback DROP TABLE IF EXISTS posts;