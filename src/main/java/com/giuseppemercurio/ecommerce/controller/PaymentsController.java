package com.giuseppemercurio.ecommerce.controller;

import com.giuseppemercurio.ecommerce.exception.PaymentNotFoundException;
import com.giuseppemercurio.ecommerce.exception.ReceiverIdNotFoundException;
import com.giuseppemercurio.ecommerce.exception.SenderIdNotFoundException;
import com.giuseppemercurio.ecommerce.model.Payment;
import com.giuseppemercurio.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/payments")
public class PaymentsController {

    @Autowired
    private PaymentService paymentService;

    // Use POST request to create a new payment
    // The request body should contain the sender ID, receiver ID, and amount
    // The response should contain the ID of the new payment
    // Add the @Valid annotation to validate the request body
    // Add the @ResponseStatus(HttpStatus.CREATED) annotation to return a 201 status code
    // Add the @ResponseBody annotation to return the response body
    // Add the @CrossOrigin annotation to allow cross-origin requests
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping
    public Payment createPayment(@Valid @RequestBody Payment payment) {
        return paymentService.createPayment(payment.getSenderId(), payment.getReceiverId(), payment.getAmount());
    }

    // Use GET request to get all payments (ordered by timestamp descending)
    // Add the @ResponseBody annotation to return the response body
    // Add the @CrossOrigin annotation to allow cross-origin requests
    // Add the @ResponseStatus(HttpStatus.OK) annotation to return a 200 status code
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping
    public List<Payment> getPayments() {
        return paymentService.getPayments();
    }

    // The following endpoints return an OK status code (200) even if the response is empty
    // This is because the response body is empty, but the status code indicates that the request was successful
    // Alternatively, you may consider returning a 204 status code (NO CONTENT)

    // Use GET request to get a single payment by its ID
    // Add the @ResponseBody annotation to return the response body
    // Add the @CrossOrigin annotation to allow cross-origin requests
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/{id}")
    public Optional<Payment> getPaymentById(@PathVariable long id) {
        return Optional.ofNullable(paymentService.getPaymentById(id).orElseThrow(() -> new PaymentNotFoundException(id)));
    }

    // Use GET request to get all payments for a given sender (ordered by timestamp descending)
    // Add the @ResponseBody annotation to return the response body
    // Add the @CrossOrigin annotation to allow cross-origin requests
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/sender/{senderId}")
    public Optional<List<Payment>> getPaymentsBySenderId(@PathVariable long senderId) {
        return Optional.ofNullable(paymentService.getPaymentsBySenderId(senderId).orElseThrow(() -> new SenderIdNotFoundException(senderId)));
    }

    // Use GET request to get all payments for a given receiver (ordered by timestamp descending)
    // Add the @ResponseBody annotation to return the response body
    // Add the @CrossOrigin annotation to allow cross-origin requests
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/receiver/{receiverId}")
    public Optional<List<Payment>> getPaymentsByReceiverId(@PathVariable long receiverId) {
        return Optional.ofNullable(paymentService.getPaymentsByReceiverId(receiverId).orElseThrow(() -> new ReceiverIdNotFoundException(receiverId)));
    }
}
