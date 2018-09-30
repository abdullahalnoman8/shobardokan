package com.tp365.shobardokan.model;

import lombok.Data;

@Data
public class Role {
    private Integer id;
    private Name name;

    public enum Name{
        ADMIN,USER
    }
}