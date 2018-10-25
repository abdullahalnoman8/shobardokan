package com.tp365.shobardokan.controller;

import com.tp365.shobardokan.model.ProductDetails;
import com.tp365.shobardokan.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Masum on 10/25/2018.
 */
@Controller
public class ProductDetailsController {

    @Autowired
    private ProductDetailsService productDetailsService;

    @RequestMapping(value = "/add")
    public String add(@ModelAttribute("add") ProductDetails productDetails, HttpServletRequest request, Model model) {
        if (request.getMethod().equals(RequestMethod.POST.toString())) {
            if (productDetailsService.add(productDetails)) {
                model.addAttribute("success", "Product data updated Successfully");
            } else {
                model.addAttribute("error", "Product data updated Unsuccessfully");
            }
        }
        model.addAttribute("productDetailsList", productDetailsService.findAll());
        return "productDetails/add";
    }

    @RequestMapping("/productDetailsList")
    public String getMeterList(Model model) {
        model.addAttribute("productDetailsList", productDetailsService.findAll());
        return "productDetails/product_details_list";
    }

}
