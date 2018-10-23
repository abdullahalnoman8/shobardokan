package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class TravelerPurchase {
    private Integer id;
    private User user;
    private Integer requestedProductId;
    private Integer purchaseInvoiceImageId;
    private RequestedStatus requestedStatus;
    private Date estimatedDate;
    private Date deliveryDate;
    private Date createdDate;
    private String comments;

    public enum RequestedStatus{
        WAITING,PURCHASED,CANCEL,ACCEPT,DELIVERED
    }
}