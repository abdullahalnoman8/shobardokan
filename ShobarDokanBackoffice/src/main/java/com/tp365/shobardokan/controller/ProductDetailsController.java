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
            model.addAttribute("success", env.getProperty("msg.update.successfull"));
        } else {
            model.addAttribute("error", env.getProperty("msg.update.unsuccessfull"));
        }
        model.addAttribute("productDetailsList", productDetailsService.findAll());
        return "productDetails/add";
    }


    @RequestMapping(value = "/editRequestedProduct", method = RequestMethod.GET)
    public String editRequestedProduct(HttpServletRequest request, Model model) {
        UserRequest userRequest = productDetailsService.requestedUncheckedProductById(Integer.valueOf(request.getParameter("id")));
        ProductDetails newProductDetails = new ProductDetails();
        newProductDetails.setUserRequest(userRequest);
        newProductDetails.setUrl(userRequest.getProductUrl());
        model.addAttribute("productDetails", newProductDetails);
        model.addAttribute("productCategory", categoryService.getProductCategoryList());
        return "productDetails/edit_requested_product";
    }

    @RequestMapping(value = "/updateRequestedProduct", method = RequestMethod.POST)
    public String updateRequestedProduct(Model model, @ModelAttribute @Valid ProductDetails productDetails, BindingResult bindingResult,
                                         final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productCategory", categoryService.getProductCategoryList());
            return "productDetails/edit_requested_product";
        } else {
            if (productDetailsService.add(productDetails)) {
                redirectAttributes.addFlashAttribute("success", env.getProperty("msg.update.successfull"));
            } else {
                redirectAttributes.addFlashAttribute("error", env.getProperty("msg.update.unsuccessfull"));
            }
            return "redirect:requestedProductList";
        }
    }

    @RequestMapping(value = "/editConfirmedProduct", method = RequestMethod.GET)
    public String editConfirmedProduct(HttpServletRequest request, Model model) {
        model.addAttribute("productDetails", productDetailsService.findById(Integer.valueOf(request.getParameter("id"))));
        model.addAttribute("productCategory", categoryService.getProductCategoryList());
        return "productDetails/edit_confirmed_product";
    }

    @RequestMapping(value = "/updateConfirmedProduct", method = RequestMethod.POST)
    public String updateConfirmedProduct(Model model, @ModelAttribute @Valid ProductDetails productDetails, BindingResult bindingResult,
                                       final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productCategory", categoryService.getProductCategoryList());
            return "productDetails/edit_confirmed_product";
        } else {
            if (productDetailsService.update(productDetails)) {
                redirectAttributes.addFlashAttribute("success", env.getProperty("msg.update.successfull"));
            } else {
                redirectAttributes.addFlashAttribute("error", env.getProperty("msg.update.unsuccessfull"));
            }
            return "redirect:confirmProductList";
        }
    }

    @RequestMapping("/requestedProductList")
    public String requestedProductList(Model model) {
        model.addAttribute("requestedProductList", productDetailsService.requestedProductList());
        return "productDetails/requested_product_list";
    }

    @RequestMapping("/confirmProductList")
    public String confirmProductList(Model model) {
        model.addAttribute("checkedProductList", productDetailsService.findAll());
        return "productDetails/confirm_product_list";
    }

}
