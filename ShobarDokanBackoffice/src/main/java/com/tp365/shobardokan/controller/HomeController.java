package com.tp365.shobardokan.controller;


import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseBody
    public String loggedIn(Model model) {

        log.info("Logged in as: " + SecurityContextHolder.getContext().getAuthentication().getName());

        return "Logged in as: " + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title", "Welcome to Smart Meter Backoffice");
//        List<User> userList = userDao.findAll();
//        System.out.println(userList.toString());
        return "login/login";
    }

    @RequestMapping(value = "/userData", method = RequestMethod.GET)
    public String userData(Model model) {
//        model.addAttribute("title", "Welcome to Smart Meter Backoffice");
        List<User> userList = userRepository.findAll();
        System.out.println(userList.toString());
        return "login/login";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model) {
        model.addAttribute("title", "Welcome to Smart Meter Backoffice");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

//        model.addAttribute("roleId", userDao.findByUserName(auth.getName()).getRole().getId());

        return "home/index";
    }
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model){
        return "register/register";
    }

    @RequestMapping(value = "/forgetPassword", method = RequestMethod.GET)
    public String forgetPassword(Model model){
        return "register/forgot-password";
    }

    @RequestMapping(value = "/403",method = RequestMethod.GET)
    public String error(Model model){
        model.addAttribute("title","ACCESS DENIED");
        return "error/403";
    }
}