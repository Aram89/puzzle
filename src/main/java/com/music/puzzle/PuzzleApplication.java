package com.music.puzzle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
//@ImportResource("classpath:security.xml")
//@EnableWebSecurity()
public class PuzzleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuzzleApplication.class, args);
	}

}
