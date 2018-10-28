package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDetails {
    private Integer id;
    private UserRequest userRequest;
    private Category category;
    private String url;
    private String productDescription;
    private Double unitPrice;
    private Double deliveryFee;
    private Status status;
    private Double costPrice;
    private Date estimatedDeliveryDate;
    private String comments;
    private User user;

    public enum Status{
        WAITING,FINISHED,ON_PROGRESS,ACCEPTED
    }
}