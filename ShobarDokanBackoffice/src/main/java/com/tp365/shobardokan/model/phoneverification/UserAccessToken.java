package com.tp365.shobardokan.model.phoneverification;

import lombok.Data;

@Data
public class UserAccessToken {
    private String id;
    private String accessToken;
    private Integer tokenRefreshIntervalSec;
}