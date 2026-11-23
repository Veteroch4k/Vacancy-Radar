package com.veteroch4k.vacancyradar;

import org.springframework.boot.SpringApplication;

public class TestVacancyradarApplication {

	public static void main(String[] args) {
		SpringApplication.from(VacancyradarApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
