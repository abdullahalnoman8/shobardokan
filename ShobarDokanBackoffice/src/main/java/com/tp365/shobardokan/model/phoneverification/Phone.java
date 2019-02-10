package com.tp365.shobardokan.model.phoneverification;

import lombok.Data;

@Data
public class Phone {
    private String number;
    private String countryPrefix;
    private String nationalNumber;

}