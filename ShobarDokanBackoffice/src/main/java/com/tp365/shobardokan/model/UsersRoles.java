package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class UsersRoles {
    private Integer id;
    private User user;
    private Role role;
    private Date createdAt;
    private Date deletedAt;
}