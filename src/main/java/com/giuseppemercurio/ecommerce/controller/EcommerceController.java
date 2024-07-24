package com.giuseppemercurio.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EcommerceController {

    @GetMapping("/api/v1/payments")
    public ResponseEntity<?> payments() {
       return new ResponseEntity<>(HttpStatus.OK);
   }
}
