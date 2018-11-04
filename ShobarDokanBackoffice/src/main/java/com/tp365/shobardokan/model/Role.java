package com.tp365.shobardokan.model;

import com.tp365.shobardokan.model.enums.Roles;
import lombok.Data;

@Data
public class Role {
    private Integer id;
    private Roles roles;
}