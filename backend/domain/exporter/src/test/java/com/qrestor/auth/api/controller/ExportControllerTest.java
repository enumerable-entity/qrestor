package com.qrestor.auth.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
public class ExportControllerTest extends AppContextTestAbstract {

    @Test
    void testExport() throws Exception {
        mockMvc.perform(get("/export/restaurant/333e1768-a544-45d5-868a-c09cab72bde3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
