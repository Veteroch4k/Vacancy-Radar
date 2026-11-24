package com.veteroch4k.vacancyradar.post;

import com.veteroch4k.vacancyradar.TestcontainersConfiguration;
import com.veteroch4k.vacancyradar.channel.Channel;
import com.veteroch4k.vacancyradar.channel.ChannelStatus;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldSaveChannelWithDefaults() {
        Channel channel = new Channel();
        channel.setUsername("java_jobs_test");

        entityManager.persist(channel);
        entityManager.flush();
        entityManager.clear();

        Channel found = entityManager.find(Channel.class, channel.getId());

        assertThat(found).isNotNull();
        assertThat(found.getId()).isNotNull();
        assertThat(found.getUsername()).isEqualTo("java_jobs_test");
        assertThat(found.getIsDefault()).isFalse();
        assertThat(found.getStatus()).isEqualTo(ChannelStatus.ACTIVE);
        assertThat(found.getConsecutiveErrors()).isZero();
        assertThat(found.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldSaveAndReadPostWithJsonbLinks() {
        Channel channel = persistChannel("progjob_test");
        Instant publishedAt = Instant.now().truncatedTo(ChronoUnit.MICROS);

        Post post = new Post();
        post.setChannel(channel);
        post.setExternalMessageId(101L);
        post.setText("Junior Java Developer (Remote)\nSpring Boot, PostgreSQL");
        post.setLinks(List.of("https://t.me/progjob/101", "https://example.com/apply"));
        post.setContentHash("a".repeat(64));
        post.setPublishedAt(publishedAt);

        Post saved = postRepository.save(post);
        entityManager.flush();
        entityManager.clear();

        Post found = postRepository.findById(saved.getId()).orElseThrow();

        assertThat(found.getExternalMessageId()).isEqualTo(101L);
        assertThat(found.getText()).contains("Junior Java Developer");
        assertThat(found.getLinks()).containsExactly("https://t.me/progjob/101", "https://example.com/apply");
        assertThat(found.getContentHash()).isEqualTo("a".repeat(64));
        assertThat(found.getPublishedAt()).isEqualTo(publishedAt);
        assertThat(found.getFetchedAt()).isNotNull();
    }

    @Test
    void shouldIgnoreDuplicatePostOnInsert() {
        Channel channel = persistChannel("javadev_test");
        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        String links = "[\"https://t.me/javadev/202\"]";

        Integer firstInsert = postRepository.insertPostIgnoreConflict(
                channel.getId(),
                202L,
                "Java Intern vacancy",
                links,
                "b".repeat(64),
                now,
                now
        );

        Integer duplicateInsert = postRepository.insertPostIgnoreConflict(
                channel.getId(),
                202L,
                "Java Intern vacancy (duplicate)",
                links,
                "b".repeat(64),
                now,
                now
        );

        assertThat(firstInsert).isEqualTo(1);
        assertThat(duplicateInsert).isZero();
        assertThat(postRepository.count()).isEqualTo(1);
    }

    private Channel persistChannel(String username) {
        Channel channel = new Channel();
        channel.setUsername(username);
        entityManager.persist(channel);
        entityManager.flush();
        return channel;
    }

}
