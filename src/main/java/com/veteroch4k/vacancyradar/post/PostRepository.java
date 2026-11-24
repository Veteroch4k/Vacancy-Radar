package com.veteroch4k.vacancyradar.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = """
            INSERT INTO posts (channel_id, external_message_id, text, links, content_hash, published_at, fetched_at)
            VALUES (:channel_id, :external_message_id, :text, CAST(:links AS jsonb), :content_hash,:published_at, :fetched_at)
            ON CONFLICT (channel_id, external_message_id) DO NOTHING;

            """, nativeQuery = true)
    @Modifying
    Integer insertPostIgnoreConflict(
            @Param("channel_id") Long channelId,
            @Param("external_message_id") Long externalMessageId,
            @Param("text") String text,
            @Param("links") String links,
            @Param("content_hash") String contentHash,
            @Param("published_at") Instant publishedAt,
            @Param("fetched_at") Instant fetchedAt
    );

}
