package com.qrestor.sellpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableDiscoveryClient
@EnableWebSecurity
@SpringBootApplication(scanBasePackages = {"com.qrestor"})
@EnableJpaRepositories(basePackages = {"com.qrestor"}, repositoryBaseClass = com.qrestor.commons.ExtendedRepositoryImpl.class)
public class SellPointApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellPointApplication.class, args);
    }

}
