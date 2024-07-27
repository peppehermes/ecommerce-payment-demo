package com.giuseppemercurio.ecommerce.controller;

import com.giuseppemercurio.ecommerce.model.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/data.sql", executionPhase = BEFORE_TEST_CLASS)
@ActiveProfiles("test")
public class PaymentControllerIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.3");

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void testCreatePayment() {
        Payment payment = new Payment(1, 2, new BigDecimal("10.00"));
        Payment response = restTemplate.postForObject("/api/v1/payments", payment, Payment.class);
        assertEquals(5, response.getId());
    }

    @Test
    public void testGetPayments() {
        Payment[] payments = restTemplate.getForObject("/api/v1/payments", Payment[].class);
        assertEquals(4, payments.length);
    }

    @Test
    public void testGetPaymentById() {
        Payment payment = restTemplate.getForObject("/api/v1/payments/1", Payment.class);
        assertEquals(1, payment.getId());
    }

    @Test
    public void testGetPaymentByIdNotFound() {
        Payment payment = restTemplate.getForObject("/api/v1/payments/100", Payment.class);
        assertNull(payment);
    }

    @Test
    public void testGetPaymentsBySenderId() {
        Payment[] payments = restTemplate.getForObject("/api/v1/payments/sender/2", Payment[].class);
        assertEquals(1, payments.length);
    }

    @Test
    public void testGetPaymentsByReceiverId() {
        Payment[] payments = restTemplate.getForObject("/api/v1/payments/receiver/1", Payment[].class);
        assertEquals(2, payments.length);
    }

    @Test
    @Sql(scripts = "/suspicious_account_detector.sql", executionPhase = BEFORE_TEST_METHOD)
    public void testSuspiciousAccountDetector() {
        Long[] accounts = restTemplate.getForObject("/api/v1/payments/suspicious", Long[].class);
        assertEquals(20, accounts[0]);
        assertEquals(10, accounts[1]);
        assertEquals(5, accounts.length);
    }

}
