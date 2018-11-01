package com.tp365.shobardokan.model;

import lombok.Data;

@Data
public class Role {
    private Integer id;
    private role name;

    public enum role {
        ADMIN, USER, TRAVELER
    }
}