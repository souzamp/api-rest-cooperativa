package com.cooperativa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiRestCooperativaApplication {
	private static Logger logger = LoggerFactory.getLogger(ApiRestCooperativaApplication.class);

	public static void main(String[] args) {
		logger.debug("Inciando a Api Cooperativa!");
		SpringApplication.run(ApiRestCooperativaApplication.class, args);
	}

}
