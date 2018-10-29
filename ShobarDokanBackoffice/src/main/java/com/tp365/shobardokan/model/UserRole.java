package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserRole {
    private Integer id;
    private User user;
    private Integer roleId;
    private Date createdAt;
    private Date deletedAt;
}