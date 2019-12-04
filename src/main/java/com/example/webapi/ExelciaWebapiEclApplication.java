package com.example.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.annotations.SwaggerDefinition;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
@SwaggerDefinition(basePath = "/api")
public class ExelciaWebapiEclApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExelciaWebapiEclApplication.class, args);
	}

}
