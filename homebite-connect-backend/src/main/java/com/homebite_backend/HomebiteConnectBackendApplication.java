package com.homebite_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@SpringBootApplication
public class HomebiteConnectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebiteConnectBackendApplication.class, args);
	}




}
