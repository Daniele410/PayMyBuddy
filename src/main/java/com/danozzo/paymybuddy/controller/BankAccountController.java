package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.service.IBankAccountService;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bankRegistration")
public class BankAccountController {
    private static final Logger logger = LogManager.getLogger("BankAccountController");

    @Autowired
    IBankAccountService bankAccountService;

    @ModelAttribute("bankAccount")
    public BankRegistrationDto bankRegistrationDto(){
        return new BankRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "bankRegistration";
    }

    @PostMapping
    public String registerBankUserAccount(@ModelAttribute("bankAccount")BankRegistrationDto bankRegistrationDto){
        bankAccountService.save(bankRegistrationDto);
        logger.info("save BankAccount");
        return "redirect:/bankRegistration?success";
    }

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
