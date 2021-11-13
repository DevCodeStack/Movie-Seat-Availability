package com.devcodestack.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieSeatAvailabilityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieSeatAvailabilityApplication.class, args);
	}

}
