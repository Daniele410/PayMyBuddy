package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.service.IBankAccountService;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BankAccountController {
    private static final Logger logger = LogManager.getLogger("BankAccountController");

    @Autowired
    IBankAccountService bankAccountService;

    @Autowired
    private UserRepository userRepository;


    //    bankRegistration
    @GetMapping("/bankAccount")
    public ModelAndView showBanks() {
        ModelAndView modelAndView = new ModelAndView("bankAccount");
        String emailConnectedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        List<BankAccount> bankAccountList = bankAccountService.getUsersBanks(emailConnectedUser);
        logger.info(bankAccountList);
        modelAndView.addObject("bankAccountList", bankAccountList);
        return modelAndView;

    }

    @ModelAttribute("bankAccount")
    public BankRegistrationDto bankRegistrationDto() {
        return new BankRegistrationDto();
    }


    @GetMapping("/bankRegistration")
    public String showRegistrationForm() {
        return "bankRegistration";
    }


    @PostMapping("/bankRegistration")
    public String registerContactFriend(@ModelAttribute("bankAccount") BankRegistrationDto bankRegistrationDto, BindingResult bindingResult) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        if (bindingResult.hasErrors()) {
            return "redirect:/bankRegistration?error";
        }
        if (bankAccountService.existsByIban(bankRegistrationDto.getIban()) == false) {
            bankAccountService.saveBank(bankRegistrationDto, user.getName());
            logger.info("save bank");
            return "redirect:/bankRegistration?success";

        } else {
            return "redirect:/bankRegistration?error";
        }
    }

    @GetMapping("/bankAccount/{id}")
    public String deleteBankById(@PathVariable int id,BankRegistrationDto bankRegistrationDto) {
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();
        List<BankAccount> bankAccountList = bankAccountService.getUsersBanks(emailConnectedUser.getName());
        bankAccountList.get(id);
        bankAccountService.deleteBankById(id);

        return "redirect:/bankAccount";
    }


//    @PostMapping
//    public String registerBankUserAccount(@ModelAttribute("bankAccount")BankRegistrationDto bankRegistrationDto){
//        bankAccountService.save(bankRegistrationDto);
//        logger.info("save BankAccount");
//        return "redirect:/bankRegistration?success";
//    }

//    @PostMapping("/update")
//    public String updateBank(String name,BankRegistrationDto bankRegistrationDto, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            bankRegistrationDto.setBankName(name);
//            return "/bankRegistrationUpdate";
//        }
//        bankAccountService.save(bankRegistrationDto);
//        return "redirect:/bankAccount";
//    }


}
