package com.giuseppemercurio.ecommerce.controller;

import com.giuseppemercurio.ecommerce.model.Payment;
import com.giuseppemercurio.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/payments")
public class PaymentsController {

    @Autowired
    private PaymentService paymentService;

    // Use GET request to get all payments (ordered by timestamp descending)
    // Add the @ResponseBody annotation to return the response body
    // Add the @CrossOrigin annotation to allow cross-origin requests
    // Add the @ResponseStatus(HttpStatus.OK) annotation to return a 200 status code
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @CrossOrigin
    @GetMapping
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
    @PostMapping
    public Payment createPayment(@Valid @RequestBody Payment payment) {
        return paymentService.createPayment(payment.getSenderId(), payment.getReceiverId(), payment.getAmount());
    }

    // The following endpoints return an OK status code (200) even if the response is empty
    // This is because the response body is empty, but the status code indicates that the request was successful
    // Alternatively, you may consider returning a 204 status code (NO CONTENT)

    // Use GET request to get a single payment by its ID
    // Add the @ResponseBody annotation to return the response body
    // Add the @CrossOrigin annotation to allow cross-origin requests
    @ResponseBody
    @CrossOrigin
    @GetMapping("/{id}")
    public Payment payment(@PathVariable long id) {
        return paymentService.getPaymentById(id);
    }

    // Use GET request to get all payments for a given sender (ordered by timestamp descending)
    // Add the @ResponseBody annotation to return the response body
    // Add the @CrossOrigin annotation to allow cross-origin requests
    @ResponseBody
    @CrossOrigin
    @GetMapping("/sender/{senderId}")
    public Iterable<Payment> paymentsBySenderId(@PathVariable long senderId) {
        return paymentService.getPaymentsBySenderId(senderId);
    }

    // Use GET request to get all payments for a given receiver (ordered by timestamp descending)
    // Add the @ResponseBody annotation to return the response body
    // Add the @CrossOrigin annotation to allow cross-origin requests
    @ResponseBody
    @CrossOrigin
    @GetMapping("/receiver/{receiverId}")
    public Iterable<Payment> paymentsByReceiverId(@PathVariable long receiverId) {
        return paymentService.getPaymentsByReceiverId(receiverId);
    }
}
