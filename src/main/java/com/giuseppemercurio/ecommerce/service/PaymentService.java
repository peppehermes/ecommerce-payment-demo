package com.giuseppemercurio.ecommerce.service;

import com.giuseppemercurio.ecommerce.model.Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentService {

    // Create a new payment
    Payment createPayment(long senderId, long receiverId, BigDecimal amount);

    // Get all payments (ordered by timestamp descending)
    List<Payment> getPayments();

    // Get a single payment by its ID
    Optional<Payment> getPaymentById(long id);

    // Get all payments for a given sender or receiver (ordered by timestamp descending)
    Optional<List<Payment>> getPaymentsBySenderId(long senderId);

    Optional<List<Payment>> getPaymentsByReceiverId(long receiverId);
}
