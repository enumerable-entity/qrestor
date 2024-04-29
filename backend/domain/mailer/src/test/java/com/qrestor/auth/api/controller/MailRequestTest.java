package com.qrestor.auth.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

@Slf4j
@WithMockUser(username = "test", roles = {"USER"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
public class MailRequestTest extends AppContextTestAbstract {

    @Test
    void testGetMailRequest() throws Exception {

    }
}
