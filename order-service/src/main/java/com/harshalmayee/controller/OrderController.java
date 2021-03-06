package com.harshalmayee.controller;

import com.harshalmayee.common.TransactionRequest;
import com.harshalmayee.common.TransactionResponse;
import com.harshalmayee.entity.Order;
import com.harshalmayee.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value = "/bookOrder",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> bookOrder(@RequestBody TransactionRequest request){
        try {
            logger.info("Booking order process Started...");
            TransactionResponse transactionResponseCreated = orderService.saveOrder(request);
            return new ResponseEntity<>(transactionResponseCreated, HttpStatus.CREATED);
        }catch (Exception e){
            logger.info("Error while booking order :"+e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
