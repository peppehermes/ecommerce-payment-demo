package com.giuseppemercurio.ecommerce.repository;

import com.giuseppemercurio.ecommerce.model.Payment;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface PaymentRepository extends ListCrudRepository<Payment, Long> {
    List<Payment> findPaymentsBySenderId(long senderId, Sort timestamp);

    List<Payment> findPaymentsByReceiverId(long receiverId, Sort timestamp);

    List<Payment> findAll(Sort timestamp);
}
