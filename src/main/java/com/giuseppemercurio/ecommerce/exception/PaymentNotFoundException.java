package com.giuseppemercurio.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(long id) {
        super("Payment with ID " + id + " not found");
    }
}
