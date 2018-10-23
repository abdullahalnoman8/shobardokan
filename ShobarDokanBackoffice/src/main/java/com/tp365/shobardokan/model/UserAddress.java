package com.tp365.shobardokan.model;

import lombok.Data;

@Data
public class UserAddress {
    private Integer id;
    private String streetAddressOne;
    private String streetAddressTwo;
    private String city;
    private String state;
    private String zipCode;
    private User user;
}