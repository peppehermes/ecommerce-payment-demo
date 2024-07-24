package com.giuseppemercurio.ecommerce.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HealthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void ebHealth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void health() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/health").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}