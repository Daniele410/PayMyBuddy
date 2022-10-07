package com.danozzo.paymybuddy.web;

import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){

        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        String cryptedPassword = passwordEncoder.encode(registrationDto.getPassword());
        userService.save(registrationDto, cryptedPassword);
        return "redirect:/registration?success";
    }
}
