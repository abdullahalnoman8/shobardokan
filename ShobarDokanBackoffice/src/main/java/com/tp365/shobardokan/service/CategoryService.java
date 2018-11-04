package com.tp365.shobardokan.service;

import com.tp365.shobardokan.model.Category;
import com.tp365.shobardokan.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Masum on 10/31/2018.
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getProductCategoryList() {
        return categoryRepository.getProductCategoryList();
    }
}
