package com.qrestor.auth.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import static com.qrestor.menu.api.RestEndpoints.MENU;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WithMockUser(username = "test", roles = {"USER"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
public class MenuController extends AppContextTestAbstract {

    @Test
    void testRegister() throws Exception {
        mockMvc.perform(post(MENU).contentType("application/json").content(
                        "{\"email\":\"mail\",\"password\":\"password\"}")
                )
                .andExpect(status().isOk());
    }
}
