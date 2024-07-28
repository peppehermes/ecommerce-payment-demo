package com.giuseppemercurio.ecommerce.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = "/sql/performance_test.sql", executionPhase = BEFORE_TEST_CLASS)
public class PaymentServicePerformanceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.3");

    @Autowired
    private PaymentServiceImpl paymentService;

    @Test
    void comparePerformanceOfSuspiciousAccountMethods() {
        long startTime, endTime, duration1, duration2, duration3;

        // Measure performance of findTopFiveSuspiciousAccounts
        startTime = System.nanoTime();
        Optional<List<Long>> result1 = paymentService.findTopFiveSuspiciousAccounts();
        endTime = System.nanoTime();
        duration1 = endTime - startTime;

        // Measure performance of findTopFiveSuspiciousAccountsUsingGraph
        startTime = System.nanoTime();
        Optional<List<Long>> result2 = paymentService.findTopFiveSuspiciousAccountsUsingGraph();
        endTime = System.nanoTime();
        duration2 = endTime - startTime;

        // Measure performance of paymentRepository.findSuspiciousAccounts
        startTime = System.nanoTime();
        Optional<List<Long>> result3 = paymentService.getSuspiciousAccounts();
        endTime = System.nanoTime();
        duration3 = endTime - startTime;

        // Print the results
        System.out.println("findTopFiveSuspiciousAccounts duration: " + duration1 + " ns");
        System.out.println("findTopFiveSuspiciousAccountsUsingGraph duration: " + duration2 + " ns");
        System.out.println("paymentRepository.findSuspiciousAccounts duration: " + duration3 + " ns");

        // Optionally, you can add assertions to ensure the results are not null
        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertTrue(result3.isPresent());
    }
}