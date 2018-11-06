package com.tp365.shobardokan.model;

import com.tp365.shobardokan.model.enums.Roles;
import com.tp365.shobardokan.model.enums.UserStatus;
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
    private Date lastActive;
    private Date createdDate;
    private Image image;
    private Roles roles;
}