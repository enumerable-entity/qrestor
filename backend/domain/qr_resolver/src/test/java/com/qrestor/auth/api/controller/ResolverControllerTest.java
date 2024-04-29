package com.qrestor.auth.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import static com.qrestor.resolverqr.api.RestEndpoints.QR;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WithMockUser(username = "test", roles = {"USER"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
public class ResolverControllerTest extends AppContextTestAbstract {

    @Test
    void testRegister() throws Exception {
        mockMvc.perform(get(QR + "/6b5d74e9-e6f5-435c-9eea-0f3c527d9961").contentType("application/json"))
                .andExpect(status().isOk());
    }
}
