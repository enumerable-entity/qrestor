package com.qrestor.resolverqr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.qrestor"})
public class QrResolverApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrResolverApplication.class, args);
    }

}
