package com.giuseppemercurio.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoPaymentFoundException extends RuntimeException {
    public NoPaymentFoundException() {
        super("No payment found on DB");
    }
}
