package com.giuseppemercurio.ecommerce.service;

import com.giuseppemercurio.ecommerce.model.Payment;
import com.giuseppemercurio.ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Implement the methods from the PaymentService interface
    // Use the PaymentRepository to interact with the database

    // Create a new payment
    // Evict the cache for the new payment, the sender ID, the receiver ID, and all payments
    // Implementing a Lazy Loading Cache, so when a new payment is created, the cache is evicted
    // This ensures that the cache is updated with the new payment

    // Disadvantage: some overhead is added to the initial response time because
    // additional roundtrips to the cache and database are needed.
    // An alternative would be to use a Write-Through Cache, where the cache is updated
    // in the same operation as the database update.
    @Override
    @Caching(evict = {
            @CacheEvict(key = "#result.id", value = "payment"),
            @CacheEvict(key = "#result.senderId", value = "senderId"),
            @CacheEvict(key = "#result.receiverId", value = "receiverId"),
            @CacheEvict(key = "'findAllPayments'", value = "payments")
    })
    public Payment createPayment(long senderId, long receiverId, BigDecimal amount) {
        Payment payment = new Payment(senderId, receiverId, amount);
        return paymentRepository.save(payment);
    }

    // Get all payments (ordered by timestamp descending)
    @Override
    @Cacheable(key = "'findAllPayments'", value = "payments")
    public List<Payment> getPayments() {
        return paymentRepository.findAllByOrderByTimestampDesc();
    }

    // Get a single payment by its ID
    @Override
    @Cacheable(key = "#id", value = "payment")
    public Optional<Payment> getPaymentById(long id) {
        return paymentRepository.findById(id);
    }

    // Get all payments for a given sender or receiver (ordered by timestamp descending)
    @Override
    @Cacheable(key = "#senderId", value = "senderId")
    public Optional<List<Payment>> getPaymentsBySenderId(long senderId) {
        return paymentRepository.findBySenderIdOrderByTimestampDesc(senderId);
    }

    @Override
    @Cacheable(key = "#receiverId", value = "receiverId")
    public Optional<List<Payment>> getPaymentsByReceiverId(long receiverId) {
        return paymentRepository.findByReceiverIdOrderByTimestampDesc(receiverId);
    }
}
