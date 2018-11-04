package com.tp365.shobardokan.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date estimatedDeliveryDate;
    private String comments;
    private User user;

    public enum Status {
        WAITING, FINISHED, ON_PROCESS, ACCEPTED
    }
}