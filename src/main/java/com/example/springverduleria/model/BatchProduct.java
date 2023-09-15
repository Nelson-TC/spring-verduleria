package com.example.springverduleria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "batch_product")
@IdClass(BatchProduct.class)
@Getter
@Setter
public class BatchProduct implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String expireDate;
    private String outDate;
    private int quantity;
    private int outQuantity;

}