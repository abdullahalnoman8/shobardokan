package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class CartItem {
    private Integer id;
    private Cart cart;
    private ProductDetails product;
    private Double unitPrice;
    private Double unitCost;
    private Integer quantity;
    private Double taxAmount;
    private Double totalAmount;
    private Double totalCost;
    private Date createdDate;
    private Date updatedDate;
}