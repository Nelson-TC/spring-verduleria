package com.example.springverduleria.controller.models;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class SaleRequest {
    @NotNull
    private List<SaleOperation> content;
}
