package com.tp365.shobardokan.controller;

import com.tp365.shobardokan.model.ProductDetails;
import com.tp365.shobardokan.model.UserRequest;
import com.tp365.shobardokan.service.CategoryService;
import com.tp365.shobardokan.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Masum on 10/25/2018.
 */
@PropertySource("classpath:message.properties")
@Controller
@RequestMapping(value = "productDetails")
public class ProductDetailsController {

    @Autowired
    private ProductDetailsService productDetailsService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private Environment env;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("productDetails") ProductDetails productDetails, Model model) {
        if (productDetailsService.add(productDetails)) {
            model.addAttribute("success", "Product data updated Successfully");
        } else {
            model.addAttribute("error", "Product data updated Unsuccessfully");
        }
        model.addAttribute("productDetailsList", productDetailsService.findAll());
        return "productDetails/add";
    }


    @RequestMapping(value = "/editUncheckedProduct", method = RequestMethod.GET)
    public String editUncheckedProduct(HttpServletRequest request, Model model) {
        UserRequest userRequest = productDetailsService.requestedUncheckedProductById(Integer.valueOf(request.getParameter("id")));
        ProductDetails newProductDetails = new ProductDetails();
        newProductDetails.setUserRequest(userRequest);
        newProductDetails.setUrl(userRequest.getProductUrl());
        model.addAttribute("productDetails", newProductDetails);
        model.addAttribute("productCategory", categoryService.getProductCategoryList());
        return "productDetails/edit_unchecked_product";
    }

    @RequestMapping(value = "/updateUncheckedProduct", method = RequestMethod.POST)
    public String updateUncheckedProduct(Model model, @ModelAttribute @Valid ProductDetails productDetails, BindingResult bindingResult,
                                         final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productCategory", categoryService.getProductCategoryList());
            return "productDetails/edit_unchecked_product";
        } else {
            if (productDetailsService.add(productDetails)) {
                redirectAttributes.addFlashAttribute("success", env.getProperty("msg.update.successfull"));
            } else {
                redirectAttributes.addFlashAttribute("error", env.getProperty("msg.update.unsuccessfull"));
            }
            return "redirect:unCheckedProductDetailsList";
        }
    }

    @RequestMapping(value = "/editCheckedProduct", method = RequestMethod.GET)
    public String editCheckedProduct(HttpServletRequest request, Model model) {
        ProductDetails newProductDetails;
        newProductDetails = productDetailsService.findById(Integer.valueOf(request.getParameter("id")));
        model.addAttribute("productDetails", newProductDetails);
        model.addAttribute("productCategory", categoryService.getProductCategoryList());
        return "productDetails/edit_checked_product";
    }

    @RequestMapping(value = "/updateCheckedProduct", method = RequestMethod.POST)
    public String updateCheckedProduct(Model model, @ModelAttribute @Valid ProductDetails productDetails, BindingResult bindingResult,
                                       final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productCategory", categoryService.getProductCategoryList());
            return "productDetails/edit_checked_product";
        } else {
            if (productDetailsService.update(productDetails)) {
                redirectAttributes.addFlashAttribute("success", env.getProperty("msg.update.successfull"));
            } else {
                redirectAttributes.addFlashAttribute("error", env.getProperty("msg.update.unsuccessfull"));
            }
            return "redirect:checkedProductDetailsList";
        }
    }

    @RequestMapping("/unCheckedProductDetailsList")
    public String uncheckedProductDetailsList(Model model) {
        model.addAttribute("requestedProductList", productDetailsService.requestedProductList());
        return "productDetails/unchecked_product_details_list";
    }

    @RequestMapping("/checkedProductDetailsList")
    public String checkedProductDetailsList(Model model) {
        model.addAttribute("checkedProductList", productDetailsService.findAll());
        return "productDetails/checked_product_details_list";
    }

}
