package com.giuseppemercurio.ecommerce.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
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
    @Min(value=0)
    @NotNull
    @Schema(example = "10")
    private long senderId;

    @Column(name = "receiver_id", nullable = false)
    @Min(value=0)
    @NotNull
    @Schema(example = "20")
    private long receiverId;

    @Column(name = "amount", nullable = false)
    @DecimalMin(value = "0.00")
    @NotNull
    @Schema(example = "100.00", type = "number", format = "decimal")
    private BigDecimal amount;

    public Payment(long senderId, long receiverId, BigDecimal amount) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
    }
}
