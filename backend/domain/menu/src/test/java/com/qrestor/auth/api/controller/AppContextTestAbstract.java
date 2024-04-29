package com.qrestor.auth.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qrestor.menu.MenuApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MenuApplication.class}, webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yml")
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
public abstract class AppContextTestAbstract {

    @Autowired
    public ObjectMapper objectMapper;
    @Autowired
    public MockMvc mockMvc;

    protected <T> ResultActions reqWitJson(RequestMethod requestMethod,
                                           String url,
                                           T requestContent)
            throws Exception {
        return mockMvc.perform(getReqBuilder(url, requestMethod).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestContent)));
    }

    protected <C, R> R performRequestAndGetObjectFromResponseAndExpectOk(
            RequestMethod requestMethod,
            String url,
            C content,
            Class<R> responseClass) throws Exception {
        return performRequestAndGetObjectFromResponseAndExpect(requestMethod, url, content, responseClass,
                status().isOk());
    }

    protected <C, R> R performRequestAndGetObjectFromResponseAndExpect(RequestMethod requestMethod,
                                                                       String url,
                                                                       C content,
                                                                       Class<R> responseClass,
                                                                       ResultMatcher... resultMatchers)
            throws Exception {
        ResultActions resultActions = reqWitJson(requestMethod, url, content);
        for (ResultMatcher resultMatcher : resultMatchers) {
            resultActions.andExpect(resultMatcher);
        }
        String response = resultActions.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, responseClass);
    }

    private MockHttpServletRequestBuilder getReqBuilder(String url,
                                                        RequestMethod requestMethod) {
        return switch (requestMethod) {
            case GET -> get(url);
            case HEAD -> head(url);
            case POST -> post(url);
            case PUT -> put(url);
            case PATCH -> patch(url);
            case DELETE -> delete(url);
            case OPTIONS -> options(url);
            default -> throw new IllegalArgumentException("Unsupported method");
        };
    }
}
