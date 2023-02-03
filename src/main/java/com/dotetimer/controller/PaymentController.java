package com.dotetimer.controller;

import com.dotetimer.domain.User;
import com.dotetimer.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/payment")
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping(path = "/ready")
    public ResponseEntity<?> readyPayment(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(paymentService.readyPayment(user), HttpStatus.OK);
    }

    @PostMapping(path = "/approve")
    public ResponseEntity<?> approvePayment(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(paymentService.approvePayment(user), HttpStatus.OK);
    }

    @PostMapping(path = "/order")
    public ResponseEntity<?> orderPayment(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(paymentService.orderPayment(user), HttpStatus.OK);
    }

    @PostMapping(path = "/cancel")
    public ResponseEntity<?> cancelPayment(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(paymentService.cancelPayment(user), HttpStatus.OK);
    }
}
