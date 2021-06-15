package com.harshalmayee.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harshalmayee.common.Payment;
import com.harshalmayee.common.TransactionRequest;
import com.harshalmayee.common.TransactionResponse;
import com.harshalmayee.controller.OrderController;
import com.harshalmayee.entity.Order;
import com.harshalmayee.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String PAYMENT_SERVICE_ENDPOINT_URL;

    private Logger logger = LoggerFactory.getLogger(OrderService.class);



    public TransactionResponse saveOrder(TransactionRequest transactionRequest) throws JsonProcessingException {
        String responseMessage="";
        Order order=transactionRequest.getOrder();
        Payment payment=transactionRequest.getPayment();
        payment.setAmount(order.getPrice());
        payment.setOrderId(order.getId());
        // Rest Call To Payment service
        logger.info("Order Service Request : {}",new ObjectMapper().writeValueAsString(transactionRequest));
        Payment paymentResponse=restTemplate.postForObject(PAYMENT_SERVICE_ENDPOINT_URL,payment,Payment.class);
        responseMessage=paymentResponse.getPaymentStatus().equalsIgnoreCase("Success")?
                "Payment Processing Successful and Order Placed Successfully" :"Error while payment processing.Order Cannot be placed";
        logger.info("Order-Service getting response from Payment-Service : {}",new ObjectMapper().writeValueAsString(paymentResponse)+" : "+new ObjectMapper().writeValueAsString(responseMessage));
        orderRepository.save(order);
        return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),responseMessage);
    }
}
