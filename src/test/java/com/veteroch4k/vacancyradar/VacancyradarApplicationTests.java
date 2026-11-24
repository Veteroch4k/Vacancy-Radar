package com.veteroch4k.vacancyradar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class VacancyradarApplicationTests {

    @Autowired
    private JdbcClient jdbcClient;

    @Test
    void contextLoadsAndDefaultSeedIsSkippedInTestContext() {
        Long channelsCount = jdbcClient.sql("SELECT count(*) FROM channels")
                .query(Long.class)
                .single();

        assertThat(channelsCount).isZero();
    }

}
