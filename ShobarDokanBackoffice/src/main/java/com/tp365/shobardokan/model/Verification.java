package com.tp365.shobardokan.model;

import lombok.Data;

import java.util.Date;

@Data
public class Verification {

    private Integer id;
    private Type type;
    private String code;
    private Date codeSentDate;
    private CodeSentThrough codeSentThrough;
    private User user;
    private Date validTillDate;
    private Boolean isSuccessful;

    public enum Type{
        PHONE,EMAIL
    }
    public enum CodeSentThrough{
        SMS,EMAIL,BOTH
    }
}