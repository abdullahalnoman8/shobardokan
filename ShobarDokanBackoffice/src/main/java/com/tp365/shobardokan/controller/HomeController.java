package com.tp365.shobardokan.controller;


import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String loggedIn(Model model) {
        log.info("Logged in as: " + SecurityContextHolder.getContext().getAuthentication().getName());
        log.info("Dashboard View For: {}",SecurityContextHolder.getContext().getAuthentication());
        return "home/dashboard";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title", "Welcome to Smart Meter Backoffice");
        return "login/login";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model) {
        log.info("Dashboard View For User: {}",SecurityContextHolder.getContext().getAuthentication());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "home/dashboard";
    }

    @RequestMapping(value = "/register")
    public String register(@ModelAttribute("register") User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        if (request.getMethod().equals(RequestMethod.POST.toString())) {
            if (userService.isRegistered(user)) {
                redirectAttributes.addFlashAttribute("success","You are successfully registered");
                return "redirect:login";
            } else {
                redirectAttributes.addFlashAttribute("error","Sorry !! Registration Failed");
                return "redirect:register";
            }
        }
        return "register/register";
    }

    @RequestMapping(value = "/forgetPassword", method = RequestMethod.GET)
    public String forgetPassword(Model model){
        return "register/forgot-password";
    }

    @RequestMapping(value = "/404",method = RequestMethod.GET)
    public String error(Model model){
        model.addAttribute("title","ACCESS DENIED");
        return "error/404";
    }
}