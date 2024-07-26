package com.giuseppemercurio.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giuseppemercurio.ecommerce.model.Payment;
import com.giuseppemercurio.ecommerce.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
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

    // setUp method
    @BeforeEach
    void setUp() {
        Payment firstPayment = new Payment(1, 2, new BigDecimal("100.0"));
        firstPayment.setId(1);
        Payment secondPayment = new Payment(2, 1, new BigDecimal("50.0"));
        secondPayment.setId(2);
        Payment thirdPayment = new Payment(3, 1, new BigDecimal("75.0"));
        thirdPayment.setId(3);
        Payment fourthPayment = new Payment(2, 3, new BigDecimal("15.0"));
        fourthPayment.setId(4);
        given(paymentService.getPaymentById(1)).willReturn(firstPayment);
        given(paymentService.getPayments()).willReturn(List.of(firstPayment, secondPayment, thirdPayment, fourthPayment));
        given(paymentService.getPaymentsBySenderId(2)).willReturn(List.of(secondPayment, fourthPayment));
        given(paymentService.getPaymentsByReceiverId(1)).willReturn(List.of(secondPayment, thirdPayment));

        // Value returned for the createPayment method
        Payment newPayment = new Payment(1, 2, new BigDecimal("100.0"));
        newPayment.setId(5);
        given(paymentService.createPayment(1, 2, new BigDecimal("100.0"))).willReturn(newPayment);
    }

    // Test the createPayment method
    @Test
    void createPaymentTest() throws Exception {
        Payment payment = new Payment(1, 2, new BigDecimal("100.0"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.senderId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.receiverId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value("100.0"));
    }

    // Test the getPayments method
    @Test
    void getPaymentsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].senderId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].receiverId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value("100.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].senderId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].receiverId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount").value("50.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].senderId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].receiverId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].amount").value("75.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].senderId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].receiverId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].amount").value("15.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)));
    }

    // Test the getPayment method
    @Test
    void getPaymentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.senderId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.receiverId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value("100.0"));
    }

    // Test the getPaymentsBySender method
    @Test
    void getPaymentsBySender() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/sender/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].senderId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].senderId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].receiverId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].receiverId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value("50.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount").value("15.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].timestamp").isNotEmpty());
    }

    // Test the getPaymentsByReceiver method
    @Test
    void getPaymentsByReceiver() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/receiver/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].senderId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].senderId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].receiverId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].receiverId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value("50.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount").value("75.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].timestamp").isNotEmpty());
    }
}