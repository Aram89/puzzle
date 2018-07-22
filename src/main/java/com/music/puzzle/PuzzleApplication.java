package com.music.puzzle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebSecurity()
@EnableSwagger2
public class PuzzleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuzzleApplication.class, args);
	}

}
