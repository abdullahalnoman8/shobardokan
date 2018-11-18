package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetails {

    private Integer id;
    private User user;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date dateOfBirth;
    private String presentAddress;
    private String mailingAddress;
    private String emergencyContactNumber;
    private Date createdDate;

    public enum Gender{
        MALE,FEMALE
    }

}