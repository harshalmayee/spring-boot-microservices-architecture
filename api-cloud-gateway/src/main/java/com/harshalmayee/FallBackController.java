package com.harshalmayee;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @RequestMapping(value = "/orderFallBack")
    public Mono<String> orderServiceFallBack(){
        return Mono.just("Order Service is taking too long to respond or is down.Please try again later");
    }

    @RequestMapping(value = "/paymentFallBack")
    public Mono<String> paymentServiceFallBack(){
        return Mono.just("Payment service is taking too long to respond or is down.Please try again later");
    }
}
