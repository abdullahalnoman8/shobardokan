package com.tp365.shobardokan.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserRequest {

    private Integer id;
    private User user;
    @NotNull
    @Size(min=2, max=300)
    @Pattern(regexp = "^((http|https)://www.(a-z|0-9).(a-z|0-9)//(a-z|0-9)-)?.+\\..+$", message = "URL Should be valid")
    private String productUrl;
    private Status status;
    @NotNull
    @Digits(integer=4, fraction=2, message = "Quantity can only be 2 digits long or less")
    private BigDecimal quantity;
    private Date createdAt;
    private Date updatedAt;
    @NotNull
    @Size(min=5, max=200,  message = "Comments must be between 5 and 200 Characters")
    @Pattern(regexp = "^[.0-9A-Za-z\\s!_:',@#*\"%+=\\$&()/-]+$", message = "Comments should be valid")
    private String comments;

    public enum Status {
        OPEN,CLOSED
    }
}