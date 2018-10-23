package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.Verification;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@Slf4j
@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestVerificationRepository {

    @Autowired
    private VerificationRepository verificationRepository;

    @Test
    public void testAdd(){
        Verification verification = new Verification();
        verification.setCode("8956");
        verification.setType(Verification.Type.PHONE);
        verification.setCodeSentThrough(Verification.CodeSentThrough.BOTH);
        User user = new User();
        user.setId(3);
        verification.setUser(user);
        verification.setValidTillDate(new Date());
        verification.setIsSuccessful(true);

        Verification savedVerification = verificationRepository.add(verification);
        Assert.assertNotNull(savedVerification.getId());
        log.info("Verification Info Added With ID: {}",savedVerification.getId());
        log.info("Verification Info Added With Data; {}",savedVerification.toString());

    }
}