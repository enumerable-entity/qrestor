package com.qrestor.mailer.sendgrid;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Value("${app.sendgrid.api-key}")
    private String sendgridApiKey;

    @Bean
    public SendGrid sendGridClient() {
        return new SendGrid(sendgridApiKey);
    }

}
