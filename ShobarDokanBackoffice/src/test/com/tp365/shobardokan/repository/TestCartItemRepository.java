package com.tp365.shobardokan.repository;

import com.tp365.shobardokan.model.Cart;
import com.tp365.shobardokan.model.CartItem;
import com.tp365.shobardokan.model.ProductDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@Slf4j
@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCartItemRepository {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    public void testAdd(){

        CartItem cartItem = new CartItem();
        Cart cart = new Cart();
        cart.setId(10);
        cartItem.setCart(cart);
        ProductDetails productDetail = new ProductDetails();
        productDetail.setId(1);
        cartItem.setProduct(productDetail);
        cartItem.setUnitPrice(79896.56);
        cartItem.setUnitCost(25874.36);
        cartItem.setQuantity(58);
        cartItem.setTotalAmount(895323.63);
        cartItem.setTaxAmount(1596.36);
        cartItem.setTotalCost(25874.28);

        CartItem savedCartItem = cartItemRepository.add(cartItem);
        Assert.assertNotNull(savedCartItem.getId());
        log.info("Added Product Detail: {}",savedCartItem);

    }
}