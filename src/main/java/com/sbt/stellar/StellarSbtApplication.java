package com.sbt.stellar;

import com.sbt.stellar.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class StellarSbtApplication {

	public static void main(String[] args) {
		SpringApplication.run(StellarSbtApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			userRepository.findAll().forEach(System.out::println);
		};
	}

/*
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configs = new CorsConfiguration();
		configs.setAllowCredentials(true);
		configs.addAllowedOrigin("*");
		configs.addAllowedHeader("*");
		configs.addAllowedMethod("OPTIONS");
		configs.addAllowedMethod("GET");
		configs.addAllowedMethod("POST");
		configs.addAllowedMethod("PUT");
		configs.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", configs);
		return new CorsFilter(source);
	}

*/

}
