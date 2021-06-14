package com.harshalmayee.service;

import com.harshalmayee.common.Payment;
import com.harshalmayee.common.TransactionRequest;
import com.harshalmayee.common.TransactionResponse;
import com.harshalmayee.entity.Order;
import com.harshalmayee.repository.OrderRepository;
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



    public TransactionResponse saveOrder(TransactionRequest transactionRequest){
        String responseMessage="";
        Order order=transactionRequest.getOrder();
        Payment payment=transactionRequest.getPayment();
        payment.setAmount(order.getPrice());
        payment.setOrderId(order.getId());
        // Rest Call To Payment service
        Payment paymentResponse=restTemplate.postForObject(PAYMENT_SERVICE_ENDPOINT_URL,payment,Payment.class);
        responseMessage=paymentResponse.getPaymentStatus().equalsIgnoreCase("Success")?
                "Payment Processing Successful and Order Placed Successfully" :"Error while payment processing.Order Cannot be placed";
        orderRepository.save(order);
        return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),responseMessage);
    }
}
