package com.example.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ExelciaWebapiEclApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExelciaWebapiEclApplication.class, args);
	}

}
