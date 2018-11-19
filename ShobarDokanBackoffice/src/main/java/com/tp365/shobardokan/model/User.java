package com.tp365.shobardokan.model;

import com.tp365.shobardokan.model.enums.Roles;
import com.tp365.shobardokan.model.enums.UserStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class User {
    private Integer id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    @Size(min = 11, max = 13)
    private String phone;
    @NotNull
    private String email;
    private UserStatus userStatus;
    private Boolean isActive;
    private Date lastActive;
    private Date createdDate;
    private Image image;
    private Roles roles;
}