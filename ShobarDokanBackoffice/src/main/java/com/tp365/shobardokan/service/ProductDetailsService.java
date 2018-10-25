package com.tp365.shobardokan.service;

import com.tp365.shobardokan.model.ProductDetails;
import com.tp365.shobardokan.repository.ProductDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Masum on 10/25/2018.
 */
@Service
public class ProductDetailsService {
    @Autowired
    private ProductDetailsRepository productDetailRepository;

    public List<ProductDetails> findAll() {
        return productDetailRepository.findAll();
    }

    public boolean add(ProductDetails productDetails) {
        return productDetailRepository.add(productDetails).getId() != null;
    }
}
