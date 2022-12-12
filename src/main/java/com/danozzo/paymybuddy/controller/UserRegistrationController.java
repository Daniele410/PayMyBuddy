package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * UserRegistrationController class allows to register new user
 */
@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    private static final Logger logger = LogManager.getLogger("UserRegistrationController");

    @Autowired
    private IUserService userService;

    /**
     * @passwordEncoder cryptage password
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){

        return new UserRegistrationDto();
    }

    /**
     * endpoint to get show form add contact
     * @return registration page
     */
    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    /**
     * @param registrationDto firstName, lastName, email, password
     * endpoint to post parameter new contact
     * @return registration?success page
     */
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        String cryptedPassword = passwordEncoder.encode(registrationDto.getPassword());
        userService.save(registrationDto, cryptedPassword);
        return "redirect:/registration?success";
    }
}
