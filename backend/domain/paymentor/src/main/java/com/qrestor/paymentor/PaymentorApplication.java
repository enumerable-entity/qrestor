package com.qrestor.paymentor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAsync
@EnableFeignClients
@ComponentScan(basePackages = {"com.qrestor"})
@EnableJpaRepositories(basePackages = {"com.qrestor"}, repositoryBaseClass = com.qrestor.commons.ExtendedRepositoryImpl.class)
public class PaymentorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentorApplication.class, args);
	}

}
