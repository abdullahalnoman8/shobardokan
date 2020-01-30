package com.tp365.shobardokan.controller;


import com.tp365.shobardokan.model.User;
import com.tp365.shobardokan.service.FacebookOtpService;
import com.tp365.shobardokan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private FacebookOtpService facebookOtpService;

    @Value("${accountkit.appsecret}")
    private String appSecret;

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

    @GetMapping(value = "/register" )
    public String register(@ModelAttribute("register") User user){
        return "register/register";
    }

    @PostMapping(value = "/register" )
    public String register(@ModelAttribute("register") User user, BindingResult bindingResult, HttpServletRequest request , RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "redirect:register";
        }else{
            if(!user.getPassword().equals(request.getParameter("password_confirmation"))){
                redirectAttributes.addFlashAttribute("error","Password miss match found");
                return "redirect:register";
            }
            if(userService.isRegistered(user)){
                redirectAttributes.addFlashAttribute("success","You are successfully registered");
                return "redirect:login";
            }else{
                redirectAttributes.addFlashAttribute("error","Sorry !! Registration Failed");
            }
        }
        return "redirect:register";
    }

    @GetMapping(value = "/forgetPassword")
    public String forgetPassword(Model model){
        return "register/forgot-password";
    }

    @GetMapping(value = "/404")
    public String error(Model model){
        model.addAttribute("title","ACCESS DENIED");
        return "error/404";
    }

//    @GetMapping(value = "/otpData")
//    public String otpData(HttpServletRequest request, Model model){
//        String code = request.getParameter("code");
//        String csrf = request.getParameter("csrf");
//        log.info("CSRF Token: {} , Authorization Code: {}",csrf,code);
//        UserAccessToken accessToken = facebookOtpService.findAccessToken(code);
//        log.info("User Access Token: {}",accessToken);
//        String appSecretProof = Utils.createAppSecretHashWithSecretKey(appSecret,accessToken.getAccessToken());
//        log.info("AppSecret Proof: {}",appSecretProof);
//        AuthorizedResponse authorizedResponse = facebookOtpService.accessTokenResponse(accessToken.getAccessToken(),appSecretProof);
//        log.info("Authorized Information: {}",authorizedResponse);
//        // Insert Phone Number Into DB
//        boolean isUpdated= false;
//        isUpdated = userService.updateUserWithValidPhone(authorizedResponse);
//        if(isUpdated){
//            log.info("Phone Number Updated with Phone {}",authorizedResponse.getPhone().getNumber());
//        }else{
//            log.info("Phone Number is  not validated: {}",isUpdated);
//        }
//        return "home/dashboard";
//    }
    
    @GetMapping(value = "/confirmEmail")
	public String confirmEmail() {
    	User user = userService.getCurrentUser();
    	if (user.getEmail() != null) {
    		// redirect user to dashboard if he already has an email set
    		return "redirect:/dashboard";
	    }
	    
    	return "login/confirm_email";
    }
}