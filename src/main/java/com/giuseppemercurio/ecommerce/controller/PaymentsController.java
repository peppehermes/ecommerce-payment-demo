package com.giuseppemercurio.ecommerce.controller;

import com.giuseppemercurio.ecommerce.model.Payment;
import com.giuseppemercurio.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentsController {

    @Autowired
    private PaymentService paymentService;

    // Use GET request to get all payments (ordered by timestamp descending)
    // Add the @ResponseBody annotation to return the response body
    // Add the @CrossOrigin annotation to allow cross-origin requests
    // Add the @ResponseStatus(HttpStatus.OK) annotation to return a 200 status code
    @ResponseBody
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/api/v1/payments", method = RequestMethod.GET)
    public Iterable<Payment> getPayments() {
        return paymentService.getPayments();
    }

    // Use POST request to create a new payment
    // The request body should contain the sender ID, receiver ID, and amount
    // The response should contain the ID of the new payment
    // Add the @Valid annotation to validate the request body
    // Add the @ResponseStatus(HttpStatus.CREATED) annotation to return a 201 status code
    // Add the @ResponseBody annotation to return the response body
    // Add the @CrossOrigin annotation to allow cross-origin requests
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/api/v1/payments", method = RequestMethod.POST)
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody Payment payment) {
        Payment newPayment = paymentService.createPayment(payment.getSenderId(), payment.getReceiverId(), payment.getAmount());
        return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
    }

    // Use GET request to get a single payment by its ID
    @GetMapping("/api/v1/payments/{id}")
    public Payment payment(@PathVariable long id) {
        return paymentService.getPaymentById(id);
    }

    // Use GET request to get all payments for a given sender or (receiver ordered by timestamp descending)
    @GetMapping("/api/v1/payments/sender/{senderId}")
    public Iterable<Payment> paymentsBySenderId(@PathVariable String senderId) {
        return paymentService.getPaymentsBySenderId(senderId);
    }

    @GetMapping("/api/v1/payments/receiver/{receiverId}")
    public Iterable<Payment> paymentsByReceiverId(@PathVariable String receiverId) {
        return paymentService.getPaymentsByReceiverId(receiverId);
    }
}
