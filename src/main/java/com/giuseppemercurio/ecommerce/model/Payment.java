package com.giuseppemercurio.ecommerce.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    // Using integer for the ID field for simplicity
    // In a real-world application, a UUID or a more complex ID generation strategy would be used
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = AccessMode.READ_ONLY)
    private long id;

    @Column(name = "timestamp")
    @Schema(accessMode = AccessMode.READ_ONLY)
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    // Using long for the sender and receiver IDs for simplicity
    // In a real-world application, a more complex user management system would be used
    @Column(name = "sender_id", nullable = false)
    private long senderId;

    @Column(name = "receiver_id", nullable = false)
    private long receiverId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    public Payment(long senderId, long receiverId, BigDecimal amount) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
    }
}
