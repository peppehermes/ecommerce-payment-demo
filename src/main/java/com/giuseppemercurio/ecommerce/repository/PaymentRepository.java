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
            SELECT account_id, COUNT(DISTINCT other_account_id) AS distinct_accounts
            FROM (
                SELECT sender_id AS account_id, receiver_id AS other_account_id
                FROM payment
                UNION ALL
                SELECT receiver_id AS account_id, sender_id AS other_account_id
                FROM payment
            ) AS combined
            GROUP BY account_id
            ORDER BY distinct_accounts DESC
            LIMIT 5""", nativeQuery = true)
    Optional<List<Long>> findSuspiciousAccounts();
}
