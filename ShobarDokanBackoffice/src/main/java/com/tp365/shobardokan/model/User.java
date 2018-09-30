package com.tp365.shobardokan.model;

import lombok.Data;

@Data
public class User {
    private Integer Id;
    private String usrName;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private Role role;

}