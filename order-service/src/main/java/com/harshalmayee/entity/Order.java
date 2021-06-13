package com.harshalmayee.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_TABLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int quantity;
    private double price;
}
