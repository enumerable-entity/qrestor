package com.qrestor.auth.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qrestor.orders.entity.OrderEntity;
import com.qrestor.orders.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.List;

import static com.qrestor.orders.api.RestEndpoints.ORDERS;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WithMockUser(username = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfig.class)
public class OrderPlacingTest extends AppContextTestAbstract {

    @Autowired
    ObjectMapper objectMapper;

    @Value("classpath:data/balancingUnitsListPrimengRequest.json")
    Resource resourceFile;

    @Autowired
    private OrderRepository orderRepository;

    public String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    void testPlaceOrder() throws Exception {
        Resource newOrderApiRequest = new ClassPathResource("requests/newOrder.json");
        mockMvc.perform(post(ORDERS)
                        .contentType(APPLICATION_JSON)
                        .content(asString(newOrderApiRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicId").isNotEmpty());
        List<OrderEntity> allOrdersInDatabase = orderRepository.findAll();
        assertEquals(1, allOrdersInDatabase.size());
    }

    @Test
    void testGetOrderItems() throws Exception {
        mockMvc.perform(get(ORDERS + "/orderId/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[0].menuItemTitle").isNotEmpty())
                .andExpect(jsonPath("$[0].menuItemId").isArray())
                .andExpect(jsonPath("$[0].menuItemOptions").isArray())
                .andExpect(jsonPath("$[0].quantity").value(2))
                .andExpect(jsonPath("$[0].specialInstructions").isNotEmpty());

    }

    @Test
    void testGetOrdersHistory() throws Exception {
        assertTrue(true);
    }

    @Test
    void testGetActiveOrders() throws Exception {
        assertTrue(true);
    }
}
