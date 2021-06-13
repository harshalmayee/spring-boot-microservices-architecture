package com.harshalmayee.service;

import com.harshalmayee.common.Payment;
import com.harshalmayee.common.TransactionRequest;
import com.harshalmayee.common.TransactionResponse;
import com.harshalmayee.entity.Order;
import com.harshalmayee.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    public TransactionResponse saveOrder(TransactionRequest transactionRequest){
        String responseMessage="";
        Order order=transactionRequest.getOrder();
        Payment payment=transactionRequest.getPayment();
        payment.setAmount(order.getPrice());
        payment.setOrderId(order.getId());
        // Rest Call To Payment service
        Payment paymentResponse=restTemplate.postForObject("http://PAYMENT-SERVICE/api/v1/doPayment",payment,Payment.class);
        responseMessage=paymentResponse.getPaymentStatus().equalsIgnoreCase("Success")?
                "Payment Processing Successful and Order Placed Successfully" :"Error while payment processing.Order Cannot be placed";
        orderRepository.save(order);
        return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),responseMessage);
    }
}
