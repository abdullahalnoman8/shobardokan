package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetails {

    private Integer id;
    private User user;
    private String first_name;
    private String last_name;
    private Gender gender;
    private Date date_of_birth;
    private String present_address;
    private String mailing_address;
    private String emergency_contact_number;
    private Date createdDate;

    public enum Gender{
        MALE,FEMALE
    }

}