package com.danozzo.paymybuddy.controller;

import java.security.Principal;
import java.util.Optional;

import com.danozzo.paymybuddy.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.service.UserServiceImpl;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private static final Logger logger = LogManager.getLogger("UserController");

    @Autowired
    private IUserService userService;

//    @GetMapping("/")
//    public String index(Model model) {
//        if(getPrincipal()!=null){
//            model.addAttribute("user", getPrincipal());
//            return "authenticated";
//        }
//        return "login";
//    }

    @GetMapping("/authenticated")
    public String authenticated(Model model) {
        model.addAttribute("user", getPrincipal());
        return "authenticated";
    }




    @GetMapping(value = "/users")
    public ModelAndView getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        logger.info("get all users");
        return modelAndView;
    }


    private User getPrincipal() {
        User user = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
    }

    @GetMapping(value = "/id")
    public Optional<User> getUserById(Integer id) {
        logger.info("get user by id");
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
        model.addAttribute("title", "Access Denied!");

        if (principal != null) {
            model.addAttribute("message", "Hi " + principal.getName()
                    + "<br> You do not have permission to access this page!");
        } else {
            model.addAttribute("msg",
                    "You do not have permission to access this page!");
        }
        return "403";
    }


}
