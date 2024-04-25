package com.qrestor.auth.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import static com.qrestor.auth.api.RestEndpoints.REGISTRATION;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WithMockUser(username = "test", roles = {"USER"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
public class RegistrationTest extends AppContextTestAbstract {

    @Test
    void testRegister() throws Exception {
        mockMvc.perform(post(REGISTRATION).contentType("application/json").content(
                        "{\"email\":\"mail\",\"password\":\"password\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(6)));
    }
}
