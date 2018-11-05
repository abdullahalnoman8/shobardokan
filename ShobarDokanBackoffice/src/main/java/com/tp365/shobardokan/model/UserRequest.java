package com.tp365.shobardokan.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class UserRequest {

    private Integer id;
    private User user;
    @NotNull
    @Size(min=2, max=300)
//    @Pattern(regexp="^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/|www\\.)[a-z0-9]+([\\-\\" +
//            ".]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$", message = "URL should be valid")
    @Pattern(regexp = "^((http|https)://www.(a-z|0-9).(a-z|0-9)//(a-z|0-9)-)?.+\\..+$", message = "URL Should be valid")
    private String productUrl;
    private Status status;
    @NotNull
    @Digits(integer=2, fraction=0, message = "Quantity can only be 2 digits long or less")
    @Min(value = 1, message = "Quantity should not be less than 1")
    @Max(value = 10, message = "Quantity should not be greater than 10")
    private int quantity;
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