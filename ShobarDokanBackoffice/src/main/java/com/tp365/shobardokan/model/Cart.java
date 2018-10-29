package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class Cart {
    private Integer id;
    private User user;
    private String promoCode;
    private Double deliveryFee;
    private Double totalAmount;
    private DiscountType discountType;
    private Double discountAmount;
    private Double paidAmount;
    private Double dueAmount;
    private Date dueDate;
    private Boolean isConfirmed;
    private Boolean isComplete;
    private Boolean isCanceled;
    private Status status;
    private UserAddress userAddress;
    private Date createdAt;
    private Date updatedAt;
    private Date deliveryAt;
    private Date deletedAt;


    public enum  DiscountType{
        PERCENTAGE,FIXED_AMOUNT
    }
    public enum Status{
        WAITING,PROGRESSING,SHIPPING,DELIVERED,CANCELED
    }
}