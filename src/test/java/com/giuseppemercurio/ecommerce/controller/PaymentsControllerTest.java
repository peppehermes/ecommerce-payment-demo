package com.giuseppemercurio.ecommerce.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createPayment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/payment").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getPayment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payment").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getPayments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getPaymentsBySenderOrReceiver() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}