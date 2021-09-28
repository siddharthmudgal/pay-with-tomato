package com.tomatopay.domaintransactions;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.tomatopay.domaintransactions.respositories.sql"})
@EnableDynamoDBRepositories(basePackages = {"com.tomatopay.domaintransactions.respositories.nosql"})
public class DomaintransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DomaintransactionsApplication.class, args);
	}

}
