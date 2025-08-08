package com.nish.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The entry point for the Terracotta Cache Api application.
 * <p>
 * This class is responsible for bootstrapping and launching the Spring Boot application. It
 * uses the {@link SpringApplication} class to start the application context and run the
 * Cache Api service. The class is annotated with {@link SpringBootApplication} to
 * enable autoconfiguration, component scanning, and other Spring Boot features.
 * </p>
 */
@SpringBootApplication
public class CacheApiApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CacheApiApplication.class);
		application.run(args);
	}

}
