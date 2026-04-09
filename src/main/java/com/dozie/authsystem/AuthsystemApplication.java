package com.dozie.authsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AuthsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthsystemApplication.class, args);
		System.out.println("Your server is running at port : 8080");
	}

}
