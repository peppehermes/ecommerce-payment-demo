package com.giuseppemercurio.ecommerce.repository;

import com.giuseppemercurio.ecommerce.model.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = "/data.sql", executionPhase = BEFORE_TEST_CLASS)
public class PaymentRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.3");

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void createConnection() {
        assertTrue(postgres.isCreated());
        assertTrue(postgres.isRunning());
    }

    @Test
    public void testFindAll() {
        List<Payment> payments = paymentRepository.findAllByOrderByTimestampDesc();
        assertEquals(4, payments.size());
        assertEquals(4, payments.get(0).getId());
        assertEquals(3, payments.get(1).getId());
        assertEquals(2, payments.get(2).getId());
        assertEquals(1, payments.get(3).getId());
    }

    @Test
    public void testFindPaymentsBySenderId() {
        Optional<List<Payment>> payments = paymentRepository.findBySenderIdOrderByTimestampDesc(1);
        assertTrue(payments.isPresent());
        assertEquals(2, payments.get().size());
        assertEquals(4, payments.get().get(0).getId());
        assertEquals(1, payments.get().get(1).getId());
    }

    @Test
    public void testFindPaymentsByReceiverId() {
        Optional<List<Payment>> payments = paymentRepository.findByReceiverIdOrderByTimestampDesc(1);
        assertTrue(payments.isPresent());
        assertEquals(2, payments.get().size());
        assertEquals(3, payments.get().get(0).getId());
        assertEquals(2, payments.get().get(1).getId());
    }

}
