package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.FriendDto;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private static final Logger logger = LogManager.getLogger("UserController");

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/authenticated")
    public String authenticated(Model model) {
        model.addAttribute("user", getPrincipal());
        logger.info("user authenticated");
        return "authenticated";
    }


    private User getPrincipal() {
        User user = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
    }

    @GetMapping(value = "/id")
    public Optional<User> getUserById(Long id) {
        logger.info("get user by id");
        return userService.getUserById(id);
    }


    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping("/contact")
    public ModelAndView showFriends() {
        ModelAndView modelAndView = new ModelAndView("contact");
        String emailConnectedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        List<User> listFriends = userService.getUsersFriends(emailConnectedUser);
        logger.info(listFriends);
        modelAndView.addObject("listFriends", listFriends);
        return modelAndView;

    }

    @GetMapping
    public String showAddContactForm() {
        return "addContact";
    }


    @GetMapping("/addContact")
    public String addContact(Model model) {
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();

        List<User> friends = userService.getUsersFriends(emailConnectedUser.getName());
        model.addAttribute("user", getPrincipal());
        return "addContact";
    }

    @PostMapping("/addContact")
    public String registerContactFriend(@ModelAttribute("User") UserRegistrationDto userRegistrationDto, BindingResult bindingResult) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        if (bindingResult.hasErrors()) {
            return "redirect:/addContact?error";
        }
        if (userService.existsByEmail(userRegistrationDto.getEmail())) {
            userService.saveFriend(userRegistrationDto.getEmail(), user.getName());
            logger.info("save contact");
            return "redirect:/addContact?success";

        }
        return "redirect:/addContact?error";

    }

    @GetMapping("/contact/{id}")
    public String deleteFriend(@PathVariable Long id) {
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();
        List<User> listFriends = userService.getUsersFriends(emailConnectedUser.getName());

        User contactToDelete = userService.getCurrentUser(emailConnectedUser.getName()).getFriends().stream()
                .filter(friend -> friend.getId().equals(id)).findFirst().orElseThrow(()-> new RuntimeException(" Id Not Found") );

        listFriends.remove(contactToDelete);
        userRepository.save(userService.getCurrentUser(emailConnectedUser.getName()));

        return "redirect:/contact";
    }



}
