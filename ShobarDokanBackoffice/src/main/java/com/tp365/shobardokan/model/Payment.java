package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class Payment {
    private Integer id;
    private Cart cart;
    private Double paidAmount;
    private PaymentMethod paymentMethod;
    private Boolean isConfirmed;
    private String token;
    private Date createdDate;
    private Date updatedDate;

    public enum PaymentMethod{
        CARD,MFS,COD
    }
}