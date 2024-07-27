package com.giuseppemercurio.ecommerce.repository;

import com.giuseppemercurio.ecommerce.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<List<Payment>> findBySenderIdOrderByTimestampDesc(long senderId);

    Optional<List<Payment>> findByReceiverIdOrderByTimestampDesc(long receiverId);

    List<Payment> findAllByOrderByTimestampDesc();

    @Query(value = """
            SELECT account_id, SUM(payment_count) AS payments
            FROM (
                SELECT sender_id AS account_id, COUNT(*) AS payment_count
                FROM payment
                GROUP BY sender_id
                UNION ALL
                SELECT receiver_id AS account_id, COUNT(*) AS payment_count
                FROM payment
                GROUP BY receiver_id
            ) AS combined
            GROUP BY account_id
            ORDER BY payments DESC
            LIMIT 5""", nativeQuery = true)
    Optional<List<Long>> findSuspiciousAccounts();
}
