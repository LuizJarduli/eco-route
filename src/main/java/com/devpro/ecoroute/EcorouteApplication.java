package com.devpro.ecoroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EcorouteApplication {

	static void main(String[] args) {
		SpringApplication.run(EcorouteApplication.class, args);
	}

}
