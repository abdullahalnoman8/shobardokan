package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDetail {
    private Integer id;
    private Integer requestId;
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