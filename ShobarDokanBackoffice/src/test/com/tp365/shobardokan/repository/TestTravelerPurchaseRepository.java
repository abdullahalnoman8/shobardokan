package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.TravelerPurchase;
import com.tp365.shobardokan.model.User;
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
public class TestTravelerPurchaseRepository {
    @Autowired
    private TravelerPurchaseRepository travelerPurchaseRepository;

    @Test
    public void testAdd(){

        TravelerPurchase travelerPurchase = new TravelerPurchase();
        User user = new User();
        user.setId(3);
        travelerPurchase.setUser(user);
        travelerPurchase.setRequestedProductId(1);
        travelerPurchase.setRequestedStatus(TravelerPurchase.RequestedStatus.WAITING);
        travelerPurchase.setPurchaseInvoiceImageId(2);
        travelerPurchase.setEstimatedDate(new Date());
        travelerPurchase.setDeliveryDate(new Date());
        travelerPurchase.setComments("Comments");

        TravelerPurchase savedTravelerPurchase = travelerPurchaseRepository.add(travelerPurchase);
        Assert.assertNotNull(savedTravelerPurchase.getId());
        log.info("Added Traveler Purchase Information: {}",savedTravelerPurchase.toString());
    }
}