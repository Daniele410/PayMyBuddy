package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @ModelAttribute("userTransfer")
    public BankRegistrationDto bankRegistrationDto() {
        return new BankRegistrationDto();
    }

    @GetMapping("/userTransfer")
    public ModelAndView showSendToTheBank(BankRegistrationDto bankAccount) {
        ModelAndView modelAndView = new ModelAndView("userTransfer");
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();

        List<BankAccount> bankAccountList = userService.getUsersBanks(emailConnectedUser.getName());
        logger.info(bankAccountList);

        modelAndView.addObject("bankAccountList", bankAccountList);
        modelAndView.addObject("bankAccount", bankRegistrationDto());

        return modelAndView;
    }

    @PostMapping("/userTransfer")
    public String sentBankAmount(BankRegistrationDto bankAccount, double balance) throws Exception {

        userService.saveUserTransfert(bankAccount, balance);
        return "redirect:/userTransfer";
    }



}
