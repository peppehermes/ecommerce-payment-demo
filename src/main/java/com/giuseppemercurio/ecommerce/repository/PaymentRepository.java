package com.giuseppemercurio.ecommerce.repository;

import com.giuseppemercurio.ecommerce.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<List<Payment>> findBySenderIdOrderByTimestampDesc(long senderId);

    Optional<List<Payment>> findByReceiverIdOrderByTimestampDesc(long receiverId);

    List<Payment> findAllByOrderByTimestampDesc();
}
