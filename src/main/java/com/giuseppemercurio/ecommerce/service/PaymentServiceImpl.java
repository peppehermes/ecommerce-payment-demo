package com.giuseppemercurio.ecommerce.service;

import com.giuseppemercurio.ecommerce.model.Payment;
import com.giuseppemercurio.ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Implement the methods from the PaymentService interface
    // Use the PaymentRepository to interact with the database

    // Create a new payment
    @Override
    public Payment createPayment(long senderId, long receiverId, BigDecimal amount) {
        Payment payment = new Payment(senderId, receiverId, amount);
        return paymentRepository.save(payment);
    }

    // Get all payments (ordered by timestamp descending)
    @Override
    public Iterable<Payment> getPayments() {
        return paymentRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    // Get a single payment by its ID
    @Override
    public Optional<Payment> getPaymentById(long id) {
        return paymentRepository.findById(id);
    }

    // Get all payments for a given sender or receiver (ordered by timestamp descending)
    @Override
    public Iterable<Payment> getPaymentsBySenderId(long senderId) {
        return paymentRepository.findPaymentsBySenderId(senderId, Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    @Override
    public Iterable<Payment> getPaymentsByReceiverId(long receiverId) {
        return paymentRepository.findPaymentsByReceiverId(receiverId, Sort.by(Sort.Direction.DESC, "timestamp"));
    }
}
