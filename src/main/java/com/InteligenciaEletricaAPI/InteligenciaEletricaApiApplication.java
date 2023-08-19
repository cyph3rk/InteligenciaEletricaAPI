package com.InteligenciaEletricaAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.InteligenciaEletricaAPI")
@EnableJpaRepositories(basePackages = "com.InteligenciaEletricaAPI")
public class InteligenciaEletricaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InteligenciaEletricaApiApplication.class, args);
	}

}
