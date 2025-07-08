package pe.edu.vallegrande.tranformacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux

public class GoalManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(GoalManagementApplication.class, args);
	}


}
