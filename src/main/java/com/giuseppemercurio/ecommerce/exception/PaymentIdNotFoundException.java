package com.giuseppemercurio.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class PaymentIdNotFoundException extends RuntimeException {
    public PaymentIdNotFoundException(long id) {
        super("Payment with ID " + id + " not found");
    }
}
