package com.giuseppemercurio.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giuseppemercurio.ecommerce.model.Payment;
import com.giuseppemercurio.ecommerce.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentsController.class)
class PaymentsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createPayment() throws Exception {
        Payment payment = new Payment(1, 2, new BigDecimal("100.0"));

        given(paymentService.createPayment(1, 2, new BigDecimal("100.0"))).willReturn(payment);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.senderId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.receiverId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value("100.0"));
    }

    @Test
    void getPayment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getPayments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/sender/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getPaymentsBySenderOrReceiver() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/receiver/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}