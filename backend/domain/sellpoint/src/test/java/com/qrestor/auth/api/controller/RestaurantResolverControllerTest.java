package com.qrestor.auth.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import static com.qrestor.sellpoint.api.RestEndpoints.RESTAURANT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WithMockUser(username = "test", roles = {"USER"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
public class RestaurantResolverControllerTest extends AppContextTestAbstract {

    @Test
    void testRegister() throws Exception {
        mockMvc.perform(post(RESTAURANT + "/e548da77-9f19-49bf-b7c4-0d00f1ab2342").contentType("application/json"))
                .andExpect(status().isOk());
    }
}
