package com.harshalmayee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.harshalmayee.entity.Payment;
import com.harshalmayee.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @RequestMapping(value = "/doPayment",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Payment> doPayment(@RequestBody Payment payment){
        try {
            logger.info("Payment Process Initiated...");
            Payment paymentCreated = paymentService.doPayment(payment);
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        }catch(Exception e){
            logger.info("Exception While Doing Payment :"+e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/findPaymentHistoryByOrderId/{orderId}")
    public ResponseEntity<Payment> findPaymentHistoryByOrderId(@PathVariable long orderId) throws JsonProcessingException {
        logger.info("Finding Payment History For OrderId :"+orderId);
        Optional<Payment> paymentData = paymentService.findPaymentHistoryByOrderId(orderId);

        if (paymentData.isPresent()) {
            return new ResponseEntity<>(paymentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
