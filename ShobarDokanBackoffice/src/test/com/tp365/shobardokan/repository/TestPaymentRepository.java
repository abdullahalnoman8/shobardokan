package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Cart;
import com.tp365.shobardokan.model.Payment;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@Slf4j
@SpringBootTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPaymentRepository {
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void testAdd(){

        Payment payment = new Payment();
        Cart cart = new Cart();
        cart.setId(11);
        payment.setCart(cart);
        payment.setPaidAmount(8974.36);
        payment.setPaymentMethod(Payment.PaymentMethod.CARD);
        payment.setIsConfirmed(true);
        payment.setToken("8547SSS");

        Payment savedPayment = paymentRepository.add(payment);
        Assert.assertNotNull(savedPayment.getId());
        log.info("Added Payment Information: {}",savedPayment.toString());
    }
}