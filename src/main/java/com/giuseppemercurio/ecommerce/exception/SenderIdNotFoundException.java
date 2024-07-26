package com.giuseppemercurio.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class SenderIdNotFoundException extends RuntimeException {
    public SenderIdNotFoundException(long id) {
        super("Sender with ID " + id + " not found");
    }
}
