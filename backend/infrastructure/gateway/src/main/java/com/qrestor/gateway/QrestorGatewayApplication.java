package com.qrestor.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class QrestorGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrestorGatewayApplication.class, args);
    }

}
