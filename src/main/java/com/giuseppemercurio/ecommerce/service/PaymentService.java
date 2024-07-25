package com.giuseppemercurio.ecommerce.service;

import com.giuseppemercurio.ecommerce.model.Payment;

import java.math.BigDecimal;

public interface PaymentService {

    // Create a new payment
    Payment createPayment(long senderId, long receiverId, BigDecimal amount);

    // Get all payments (ordered by timestamp descending)
    Iterable<Payment> getPayments();

    // Get a single payment by its ID
    Payment getPaymentById(long id);

    // Get all payments for a given sender or receiver (ordered by timestamp descending)
    Iterable<Payment> getPaymentsBySenderId(long senderId);

    Iterable<Payment> getPaymentsByReceiverId(long receiverId);
}
