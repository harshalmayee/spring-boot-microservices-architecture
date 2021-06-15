package com.harshalmayee.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harshalmayee.entity.Payment;
import com.harshalmayee.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public Payment doPayment(Payment payment) throws JsonProcessingException {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        logger.info("Payment Service Request :{} ",new ObjectMapper().writeValueAsString(payment));
        return paymentRepository.save(payment);
    }

    private String paymentProcessing(){
        // API should be 3rd party Payment Gateway like PayTM,PayPal...
        return new Random().nextBoolean()?"Success":"false";
    }

    public Optional<Payment> findPaymentHistoryByOrderId(long orderId) throws JsonProcessingException {
        Optional<Payment> payment=paymentRepository.findByOrderId(orderId);
        logger.info("Payment-Service findPaymentHistoryByOrderId : {}",new ObjectMapper().writeValueAsString(payment));
        return payment ;
    }
}
