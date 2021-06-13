package com.harshalmayee.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "PAYMENT_TABLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long paymentId;
    private String paymentStatus;
    private String transactionId;
    private long orderId;
    private double amount;
}
