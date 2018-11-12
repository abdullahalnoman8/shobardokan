package com.tp365.shobardokan.model;

import com.tp365.shobardokan.model.enums.Status;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductDetails {

    private Integer id;

    @NotNull
    private UserRequest userRequest;

    private Category category;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^((http|https)://www.(a-z|0-9).(a-z|0-9)//(a-z|0-9)-)?.+\\..+$", message = "URL Should be valid")
    private String url;

    @Size(min = 5, message = "Description should be more 5 Characters")
    @Pattern(regexp = "^[.0-9A-Za-z\\s!_:',@#*\"%+=\\$&()/-]+$", message = "Description should be valid")
    private String productDescription;

    @Digits(integer = 6, fraction = 2, message = "Delivery Fee should be more 0")
    private BigDecimal deliveryFee;

    @NotNull
    @Valid
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Digits(integer = 4, fraction = 2, message = "Quantity can only be 2 digits long or less")
    private BigDecimal unitPrice;

    @NotNull
    @Digits(integer = 6, fraction = 2, message = "Cost Price should be more 0")
    private BigDecimal costPrice;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Estimated Delivery Date Should be Future Date")
    private Date estimatedDeliveryDate;

    private String comments;

    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date priceValidTill;
}