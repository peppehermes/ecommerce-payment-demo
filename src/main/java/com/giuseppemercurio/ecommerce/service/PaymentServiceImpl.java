package com.giuseppemercurio.ecommerce.service;

import com.giuseppemercurio.ecommerce.model.Payment;
import com.giuseppemercurio.ecommerce.repository.PaymentRepository;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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
            @CacheEvict(key = "'findAllPayments'", value = "payments"),
            @CacheEvict(key = "'suspiciousAccounts'", value = "suspiciousAccounts")
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

    @Override
    @Cacheable(key = "'suspiciousAccounts'", value = "suspiciousAccounts")
    public Optional<List<Long>> getSuspiciousAccounts() {
        return paymentRepository.findSuspiciousAccounts();
    }

    Optional<List<Long>> findTopFiveSuspiciousAccounts() {
        HashMap<Long, Set<Long>> suspiciousAccounts = new HashMap<>();
        List<Payment> payments = paymentRepository.findAll();

        for (Payment payment : payments) {
            suspiciousAccounts.computeIfAbsent(payment.getSenderId(), k -> new HashSet<>()).add(payment.getReceiverId());
            suspiciousAccounts.computeIfAbsent(payment.getReceiverId(), k -> new HashSet<>()).add(payment.getSenderId());
        }

        List<Long> topFiveSuspiciousAccounts = suspiciousAccounts.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue().size(), a.getValue().size()))
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();

        return Optional.of(topFiveSuspiciousAccounts);
    }

    Optional<List<Long>> findTopFiveSuspiciousAccountsUsingGraph() {
        Graph<Long, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        paymentRepository.findAll().forEach(payment -> {
            graph.addVertex(payment.getSenderId());
            graph.addVertex(payment.getReceiverId());
            graph.addEdge(payment.getSenderId(), payment.getReceiverId());
        });

        return Optional.of(graph.vertexSet().stream()
                .sorted(Comparator.comparingInt(graph::degreeOf).reversed())
                .limit(5)
                .toList());
    }
}
