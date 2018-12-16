package com.music.puzzle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@ComponentScan
@SpringBootApplication
@EnableWebSecurity
@EnableSwagger2
public class PuzzleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuzzleApplication.class, args);
	}

}
