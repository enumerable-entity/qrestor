package com.qrestor.auth.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import static com.qrestor.feedback.api.controller.RestEndpoints.FEEDBACK_PUBLISH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WithMockUser(username = "test", roles = {"USER"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
public class FeedbackTest extends AppContextTestAbstract {

    @Test
    void testGetFeedback() throws Exception {
        mockMvc.perform(post(FEEDBACK_PUBLISH).contentType("application/json"))
                .andExpect(status().isOk());
    }
}
