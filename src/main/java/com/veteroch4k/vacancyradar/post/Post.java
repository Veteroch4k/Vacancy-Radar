package com.veteroch4k.vacancyradar.post;

import com.veteroch4k.vacancyradar.channel.Channel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "channel_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Channel channel;

    @Column(name = "external_message_id")
    private Long externalMessageId;

    private String text;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<String> links = new ArrayList<>();

    @Column(name = "content_hash")
    private String  contentHash;

    @Column(name = "published_at")
    private Instant publishedAt;

    @Column(name = "fetched_at")
    private Instant fetchedAt = Instant.now();


}
