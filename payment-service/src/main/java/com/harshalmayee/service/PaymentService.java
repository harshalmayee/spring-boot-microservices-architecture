package com.harshalmayee.service;

import com.harshalmayee.entity.Payment;
import com.harshalmayee.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment doPayment(Payment payment){
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    private String paymentProcessing(){
        // API should be 3rd party Payment Gateway like PayTM,PayPal...
        return new Random().nextBoolean()?"Success":"false";
    }
}
