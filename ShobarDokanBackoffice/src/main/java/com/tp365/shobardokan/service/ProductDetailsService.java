package com.tp365.shobardokan.service;

import com.tp365.shobardokan.model.ProductDetails;
import com.tp365.shobardokan.model.UserRequest;
import com.tp365.shobardokan.repository.ProductDetailsRepository;
import com.tp365.shobardokan.repository.UserRequestRepository;
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
    @Autowired
    private UserRequestRepository userRequestRepository;

    public List<ProductDetails> findAll() {
        return productDetailRepository.findAll();
    }

    public ProductDetails findById(Integer id) {
        return productDetailRepository.findById(id);
    }

    public boolean add(ProductDetails productDetails) {
        if (productDetailRepository.add(productDetails).getId() != null){
            return userRequestRepository.updateUserRequestStatus(productDetails.getUserRequest().getId());
        }
        return false;
    }

    public List<UserRequest> requestedProductList() {
        return productDetailRepository.requestedProductList();
    }

    public UserRequest requestedUncheckedProductById(Integer id) {
        return productDetailRepository.requestedUncheckedProductById(id);
    }


    public boolean update(ProductDetails productDetails) {
        return productDetailRepository.update(productDetails);
    }
}
