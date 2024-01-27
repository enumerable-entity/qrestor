package com.qrestor.resolverqr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.qrestor"})
@EnableJpaRepositories(basePackages = {"com.qrestor"}, repositoryBaseClass = com.qrestor.commons.ExtendedRepositoryImpl.class)
public class QrResolverApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrResolverApplication.class, args);
    }

}
