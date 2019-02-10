package com.tp365.shobardokan.service;

import com.tp365.shobardokan.model.phoneverification.Application;
import com.tp365.shobardokan.model.phoneverification.AuthorizedResponse;
import com.tp365.shobardokan.model.phoneverification.Phone;
import com.tp365.shobardokan.model.phoneverification.UserAccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class FacebookOtpService {
    @Value("${phone_user_access_token.url}")
    private String url;


    @Value("${account_kit.appId}")
    private String appId;
    @Value("${accountkit.appsecret}")
    private String appSecret;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public UserAccessToken findAccessToken(String authorizationCode){
        RestTemplate restTemplate = restTemplateBuilder.build();
        String authorizationCodeUrl = String.format(this.url,authorizationCode.trim(),appId.trim(),appSecret.trim());
        ParameterizedTypeReference<Map<String, Object>> typeReference = new ParameterizedTypeReference<Map<String, Object>>() {};
        ResponseEntity<Map<String,Object>> responseEntity = restTemplate.exchange(authorizationCodeUrl, HttpMethod.GET, null, typeReference);
        log.info("Response Code For Getting UserAccessToken:{}",responseEntity.getStatusCode());
        Map<String, Object> response = responseEntity.getBody();
        UserAccessToken userAccessToken = new UserAccessToken();
        userAccessToken.setAccessToken(String.valueOf(response.get("access_token")));
        userAccessToken.setId(String.valueOf(response.get("id")));
        userAccessToken.setTokenRefreshIntervalSec((Integer) response.get("token_refresh_interval_sec"));
        return userAccessToken;
    }

    public AuthorizedResponse accessTokenResponse(String accessToken, String appSecretProof){
        RestTemplate restTemplate = restTemplateBuilder.build();
        String accessTokenValidationUrl = "https://graph.accountkit.com/v1.3/me/?access_token=%s&appsecret_proof=%s";
        String accessTokenUrl = String.format(accessTokenValidationUrl,accessToken,appSecretProof);
        log.info("Access Token Validation URL: {}",accessTokenUrl);
        ParameterizedTypeReference<Map<String, Object>> typeReference = new ParameterizedTypeReference<Map<String, Object>>() {};
        ResponseEntity<Map<String,Object>> responseEntity = restTemplate.exchange(accessTokenUrl, HttpMethod.GET, null, typeReference);
        log.info("Authorized Response Code: {}",responseEntity.getStatusCode());
        Map<String, Object> response = responseEntity.getBody();
        log.info("Response Map Data {}", response);
        AuthorizedResponse authorizedResponse = new AuthorizedResponse();
        if (response.containsKey("phone")) {
            Map<String, Object> phoneData = (Map<String, Object>) response.get("phone");
            log.info("Phone Data: {}",phoneData);
            Phone phone = new Phone();
            phone.setNumber(String.valueOf(phoneData.get("number")));
            phone.setNationalNumber(String.valueOf(phoneData.get("national_number")));
            phone.setCountryPrefix(String.valueOf(phoneData.get("country_prefix")));
            Application application = new Application();
            Map<String,Object> applicationData = (Map<String, Object>) response.get("application");
            application.setId(String.valueOf(applicationData.get("id")));
            authorizedResponse.setPhone(phone);
            authorizedResponse.setApplication(application);
            authorizedResponse.setId(String.valueOf(response.get("id")));
        }
        return authorizedResponse;
    }
}