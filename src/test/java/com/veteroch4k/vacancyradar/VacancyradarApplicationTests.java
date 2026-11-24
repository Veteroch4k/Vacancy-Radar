package com.veteroch4k.vacancyradar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class VacancyradarApplicationTests {

	@Autowired
	private JdbcClient jdbcClient;


	@Test
	void contextLoads() {

	}

	@Test
	void seedLoading() {

		Integer count = jdbcClient
				.sql("SELECT count(*) FROM channels")
				.query(Integer.class)
				.single();

		assertEquals(0, count);


	}


}
