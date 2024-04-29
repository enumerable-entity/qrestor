package com.qrestor.auth.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@Import({TestConfig.class})
@TestPropertySource(locations = "classpath:application.yml")
public class TestApplication {

    @Test
    void contextLoads() {

    }
}
