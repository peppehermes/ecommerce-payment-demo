package com.giuseppemercurio.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ReceiverIdNotFoundException extends RuntimeException {
    public ReceiverIdNotFoundException(long id) {
        super("Receiver with ID " + id + " not found");
    }
}
