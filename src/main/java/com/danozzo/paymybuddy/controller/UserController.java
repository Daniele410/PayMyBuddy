package com.danozzo.paymybuddy.controller;

import java.security.Principal;
import java.util.Optional;

import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.service.UserServiceImpl;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private static final Logger logger = LogManager.getLogger("UserController");

    @Autowired
    private IUserService userService;


    @GetMapping("/authenticated")
    public String authenticated(Model model) {
        model.addAttribute("user", getPrincipal());
        logger.info("user authenticated");
        return "authenticated";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("user", getPrincipal());
        return "contact";
    }

    @GetMapping("/addContact")
    public String addContact(Model model) {
        model.addAttribute("user", getPrincipal());
        return "addContact";
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


    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showAddContactForm() {
        return "addContact";
    }

    @PostMapping("/addContact")
    public String registerContactFriend(@ModelAttribute("user") UserRegistrationDto userRegistrationDto, String email, BindingResult bindingResult, Model model) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        if (bindingResult.hasErrors()) {
            return "addContact";
        }
        logger.info("add contact friend");
        if ( userService.existsByEmail(userRegistrationDto.getEmail()) ) {
           userService.saveFriend(userRegistrationDto.getEmail(), user.getName());
            bindingResult.rejectValue("email", "", "This email already exists");
            return "addContact";
        }

        return "redirect:/addContact?success";
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
