package com.veteroch4k.vacancyradar.channel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "channels")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String title;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Enumerated(EnumType.STRING)
    private ChannelStatus status = ChannelStatus.ACTIVE;

    @Column(name = "last_message_id")
    private Long lastMessageId;

    @Column(name = "last_fetched_at")
    private Instant lastFetchedAt;

    @Column(name = "consecutive_errors")
    private Integer consecutiveErrors = 0;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();


}
