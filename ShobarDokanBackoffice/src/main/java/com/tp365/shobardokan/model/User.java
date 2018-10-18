package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private UserStatus userStatus;
    private Boolean isActive;
    private Date lastActiveDate;
    private Date createdDate;
    private Role role;
    private Image image;

    public enum UserStatus{
        ACTIVE,INACTIVE,UNVERIFIED_REGISTRATION
    }
}