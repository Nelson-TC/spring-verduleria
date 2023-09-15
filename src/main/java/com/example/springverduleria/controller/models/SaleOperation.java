package com.example.springverduleria.controller.models;

import lombok.Getter;

@Getter
public class SaleOperation {
    private Long id;
    private int quantity;
    private String name;
    private double unitPrice;
}
