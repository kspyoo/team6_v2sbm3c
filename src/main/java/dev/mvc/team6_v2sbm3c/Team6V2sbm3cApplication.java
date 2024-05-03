package dev.mvc.team6_v2sbm3c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"dev.mvc"})
public class Team6V2sbm3cApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team6V2sbm3cApplication.class, args);
	}

}
