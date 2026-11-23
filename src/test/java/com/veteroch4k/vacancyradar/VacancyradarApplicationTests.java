package com.veteroch4k.vacancyradar;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class VacancyradarApplicationTests {

	@Test
	void contextLoads() {
	}

}
