package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.TransferRepository;
import com.danozzo.paymybuddy.service.ITransferService;
import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.web.dto.FriendDto;
import com.danozzo.paymybuddy.web.dto.TransferDto;
import exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
public class TransferController {
    private static final Logger logger = LogManager.getLogger("TransferController");

    @Autowired
    public ITransferService transferService;

    @Autowired
    IUserService userService;

    @Autowired
    TransferRepository transferRepository;


    @ModelAttribute("transfer")
    public FriendDto friendDto() {
        return new FriendDto();
    }


    @GetMapping("/transfer")
    public ModelAndView showReceivedPaymentsPage(TransferDto transferDto) {
        ModelAndView modelAndView = new ModelAndView("transfer");
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();

        Set<Transfer> receivedPayments = userService.getReceivedPayments(emailConnectedUser.getName());
        logger.info("receivedPayments: " + receivedPayments);
        modelAndView.addObject("receivedPayments", receivedPayments);

        Set<Transfer> sentPayments = userService.getSentPayment(emailConnectedUser.getName());
        logger.info("sentPayments: "+sentPayments);
        modelAndView.addObject("sentPayments", sentPayments);

        List<User> listFriends = userService.getUsersFriends(emailConnectedUser.getName());
        logger.info(listFriends);
        modelAndView.addObject("listFriends", listFriends);
        modelAndView.addObject("transfer", transferDto);

        return modelAndView;
    }

    @PostMapping("/transaction")
    public String sentAmount(TransferDto transfer, String email ,double amount) throws UserNotFoundException {

        transferService.saveTransfert(transfer, amount);
        return "redirect:/transfer";
    }


}
