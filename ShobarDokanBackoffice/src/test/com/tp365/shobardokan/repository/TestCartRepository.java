package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Cart;
import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.model.UserAddress;
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
public class TestCartRepository {
    @Autowired
    private CartRepository cartRepository;

    @Test
    public void testAdd(){
        Cart cart = new Cart();
        User user = new User();
        user.setId(3);
        cart.setUser(user);
        cart.setPromoCode("895");
        cart.setDeliveryFee(8956.23);
        cart.setTotalAmount(123654.36);
        cart.setDiscountAmount(25874.63);
        cart.setDiscountType(Cart.DiscountType.FIXED_AMOUNT);
        cart.setPaidAmount(8796.63);
        cart.setDueAmount(2541.85);
        UserAddress userAddress = new UserAddress();
        userAddress.setId(1);
        cart.setUserAddress(userAddress);
        cart.setIsConfirmed(true);
        cart.setIsComplete(true);
        cart.setIsCanceled(false);
        cart.setStatus(Cart.Status.DELIVERED);
        cart.setDeliveryAt(new Date());

        Cart savedCart = cartRepository.add(cart);
        Assert.assertNotNull(savedCart.getId());
        log.info("Added Cart Detail: {}",savedCart);
    }
}