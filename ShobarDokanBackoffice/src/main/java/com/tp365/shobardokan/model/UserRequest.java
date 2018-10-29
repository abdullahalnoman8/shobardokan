package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserRequest {

    private Integer id;
    private User user;
    private String productUrl;
    private Status status;
    private Integer quantity;
    private Date createdAt;
    private Date updatedAt;
    private String comments;

    public enum Status{
        OPEN,CLOSED
    }
}