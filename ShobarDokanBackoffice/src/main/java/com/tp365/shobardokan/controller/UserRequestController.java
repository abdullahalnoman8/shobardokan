package com.tp365.shobardokan.controller;

import com.tp365.shobardokan.model.UserRequest;
import com.tp365.shobardokan.service.UserRequestService;
import com.tp365.shobardokan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/requestProduct")
public class UserRequestController {

    @Autowired
    UserService userService;
    @Autowired
    UserRequestService userRequestService;

    @RequestMapping(value = "/list")
    public String getAllRequestList(Model model) {
        List<UserRequest> requestedProductList = userRequestService.findAllRequestedProducts();
        model.addAttribute("requestedProductList", requestedProductList);
        return "product_request/product_request_list";
    }

    @RequestMapping("/add")
    public String add(@ModelAttribute @Valid UserRequest userRequest, BindingResult bindingResult,
                      HttpServletRequest request, final RedirectAttributes redirectAttributes){
        if (request.getMethod().equals(RequestMethod.POST.toString())) {
            if (bindingResult.hasErrors()) {
                return "product_request/product_request_add";
            } else {
                if (userRequestService.add(userRequest)) {
                    redirectAttributes.addFlashAttribute("success", "Request received Successfully");
                    return "redirect:list";
                } else {
                    redirectAttributes.addFlashAttribute("error", "Request received Failed");
                    return "redirect:/requestProduct/add";
                }
            }

        }
        return "product_request/product_request_add";
    }

}
