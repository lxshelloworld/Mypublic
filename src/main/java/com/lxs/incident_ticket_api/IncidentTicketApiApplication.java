package com.lxs.incident_ticket_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IncidentTicketApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncidentTicketApiApplication.class, args);
	}

}