package dev.mvc.team6_v2sbm3c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"dev.mvc"})
@EnableJpaRepositories(basePackages = {"dev.mvc.jpa"})
@EntityScan(basePackages = {"dev.mvc.jpa"})
public class Team6V2sbm3cApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team6V2sbm3cApplication.class, args);
	}

}
