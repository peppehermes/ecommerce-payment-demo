package com.giuseppemercurio.ecommerce.repository;

import com.giuseppemercurio.ecommerce.model.Payment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
    @Query(value = "SELECT * FROM Payment p where p.sender_id = ?1 ORDER BY p.timestamp DESC", nativeQuery = true)
    Iterable<Payment> findAllBySenderId(long senderId);

    @Query(value = "SELECT * FROM Payment p where p.receiver_id = ?1 ORDER BY p.timestamp DESC", nativeQuery = true)
    Iterable<Payment> findAllByReceiverId(long receiverId);

    Iterable<Payment> findAll(Sort timestamp);
}
