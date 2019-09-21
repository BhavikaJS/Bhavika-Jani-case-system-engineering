package com.assignment.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main class of the spring boot application
 * 
 * @author BJani
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.assignment.controller", "com.assignment.service" })
@EnableJpaRepositories("com.assignment.repository")
@EntityScan("com.assignment.model")
public class UserManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementServiceApplication.class, args);
	}

}
