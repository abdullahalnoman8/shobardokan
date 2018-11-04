package com.tp365.shobardokan.controller;

import com.tp365.shobardokan.model.ProductDetails;
import com.tp365.shobardokan.service.CategoryService;
import com.tp365.shobardokan.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

    @RequestMapping(value = "/add")
    public String add(@ModelAttribute("productDetails") ProductDetails productDetails, HttpServletRequest request, Model model) {
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

    @RequestMapping("/editUncheckedProduct")
    public String edit(Model model, @ModelAttribute("productDetails") ProductDetails productDetails,
                       HttpServletRequest request, final RedirectAttributes redirectAttributes) throws IOException {
        if (request.getMethod().equals(RequestMethod.POST.toString())) {
            if (productDetailsService.add(productDetails)) {
                redirectAttributes.addFlashAttribute("success", env.getProperty("msg.update.successfull"));
            } else {
                redirectAttributes.addFlashAttribute("error", env.getProperty("msg.update.unsuccessfull"));
            }
            return "redirect:unCheckedProductDetailsList";
        }
        model.addAttribute("requestedProductInfo", productDetailsService.requestedUncheckedProductById(productDetails.getId()));
        model.addAttribute("productCategory", categoryService.getProductCategoryList());
        return "productDetails/edit_unchecked_product";
    }

    @RequestMapping("/editCheckedProduct")
    public String editCheckedProduct(Model model, @ModelAttribute("productDetails") ProductDetails productDetails,
                                     HttpServletRequest request, final RedirectAttributes redirectAttributes) throws IOException {
        if (request.getMethod().equals(RequestMethod.POST.toString())) {
            if (productDetailsService.update(productDetails)) {
                redirectAttributes.addFlashAttribute("success", env.getProperty("msg.update.successfull"));
            } else {
                redirectAttributes.addFlashAttribute("error", env.getProperty("msg.update.unsuccessfull"));
            }
            return "redirect:checkedProductDetailsList";
        }
        model.addAttribute("productDetails", productDetailsService.findById(productDetails.getId()));
        model.addAttribute("productCategory", categoryService.getProductCategoryList());
        return "productDetails/edit_checked_product_details";
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
