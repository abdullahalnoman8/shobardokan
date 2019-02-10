package com.tp365.shobardokan.model.phoneverification;

import lombok.Data;

@Data
public class AuthorizedResponse {
    private String id;
    private Phone phone;
    private Application application;
}