package com.giuseppemercurio.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SameSenderReceiverException extends RuntimeException {
    public SameSenderReceiverException() {
        super("Sender and receiver cannot have same ID");
    }
}
