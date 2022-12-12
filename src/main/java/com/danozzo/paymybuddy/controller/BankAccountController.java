package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.service.IBankAccountService;
import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.TransferDto;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import exception.UserNotFoundException;
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
/**
 * BankAccountController class allows to do CRUD operations for bankAccount
 */
@Controller
public class BankAccountController {
    private static final Logger logger = LogManager.getLogger("BankAccountController");

    @Autowired
    IBankAccountService bankAccountService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    IUserService userService;


    /**
     * endpoint to get list of banks of current user
     * @return modelAndView of bankAccount
     */
    @GetMapping("/bankAccount")
    public ModelAndView showBanks() {
        ModelAndView modelAndView = new ModelAndView("bankAccount");
        String emailConnectedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        List<BankAccount> bankAccountList = bankAccountService.getUsersBanks(emailConnectedUser);
        logger.info(bankAccountList);
        modelAndView.addObject("bankAccountList", bankAccountList);
        return modelAndView;

    }

    /**
     * @return new BankRegistrationDto
     */
    @ModelAttribute("bankAccount")
    public BankRegistrationDto bankRegistrationDto() {
        return new BankRegistrationDto();
    }


    /**
     * endpoint to show form bankRegistration
     * @return bankRegistration
     */
    @GetMapping("/bankRegistration")
    public String showRegistrationForm() {
        return "bankRegistration";
    }


    /**
     * @param bankRegistrationDto String bankName, String iban, String location
     * @param bindingResult
     * endpoint to post new Bank
     * if true
     * @return bankRegistration?success
     */
    @PostMapping("/bankRegistration")
    public String registerBankUser(@ModelAttribute("bankAccount") BankRegistrationDto bankRegistrationDto, BindingResult bindingResult) {
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

    /**
     * @param id
     * delete bank by id of listBank userConnected
     * @return bankAccount
     */
    @GetMapping("/bankAccount/{id}")
    public String deleteBankById(@PathVariable Long id) {

        bankAccountService.deleteBankById((id));

        return "redirect:/bankAccount";
    }

    /**
     * @param bankAccount
     * endpoint to show page user transfer to bank
     * @return modelAndView bankTransfer
     */
    @GetMapping("/bankTransfer")
    public ModelAndView showSendToTheBank(BankRegistrationDto bankAccount) {
        ModelAndView modelAndView = new ModelAndView("bankTransfer");
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();

        List<BankAccount> bankAccountList = userService.getUsersBanks(emailConnectedUser.getName());
        logger.info(bankAccountList);

        modelAndView.addObject("bankAccountList", bankAccountList);
        modelAndView.addObject("bankAccount", bankRegistrationDto());

        return modelAndView;
    }

    /**
     * @param bankAccount
     * @param balance
     * endpoint save transfer user to bank
     * @return bankTransfer page
     * @throws Exception
     */
    @PostMapping("/bankTransfer")
    public String sentBankAmount(BankRegistrationDto bankAccount, double balance) throws Exception {

        bankAccountService.saveBankTransfert(bankAccount, balance);
        return "redirect:/bankTransfer";
    }


}
