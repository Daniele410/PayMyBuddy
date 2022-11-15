package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.repository.TransferRepository;
import com.danozzo.paymybuddy.service.ITransferService;
import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.web.dto.FriendDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class TransferController {
    private static final Logger logger = LogManager.getLogger("TransferController");

    @Autowired
    public ITransferService iTransferService;

    @Autowired
    IUserService userService;

    @Autowired
    TransferRepository transferRepository;



    @ModelAttribute("transfer")
    public FriendDto friendDto() {
        return new FriendDto();
    }


    @GetMapping("/transfer")
    public ModelAndView showReceivedPaymentsPage() {
        ModelAndView modelAndView = new ModelAndView("transfer");
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();

        Set<Transfer> receivedPayments = userService.getReceivedPayments(emailConnectedUser.getName());
        logger.info("receivedPayments: " + receivedPayments);
        modelAndView.addObject("receivedPayments", receivedPayments);

        Set<Transfer> sentPayments = userService.getSentPayment(emailConnectedUser.getName());
        logger.info("sentPayments: "+sentPayments);
        modelAndView.addObject("sentPayments", sentPayments);

        return modelAndView;
    }



}
