package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.service.ITransferService;
import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.web.dto.FriendDto;
import com.danozzo.paymybuddy.web.dto.TransferDto;
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

import java.math.BigDecimal;
import java.util.List;

@Controller
public class TransactionController {
    private static final Logger logger = LogManager.getLogger("TransactionController");

    @Autowired
    private IUserService userService;

    @Autowired
    private ITransferService transferService;


//    @GetMapping("transaction")
//    public String showTransactionPage() {
//        return "transaction";
//    }

    @ModelAttribute("transaction")
    public FriendDto friendDto() {
        return new FriendDto();
    }

    @ModelAttribute("transfer")
    public TransferDto transferDto() {
        return new TransferDto();
    }

    @GetMapping("/transaction")
    public ModelAndView showFormTransaction(TransferDto transferDto) {
        ModelAndView modelAndView = new ModelAndView("transaction");
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();
        List<User> listFriends = userService.getUsersFriends(emailConnectedUser.getName());
        logger.info(listFriends);
        modelAndView.addObject("listFriends", listFriends);
        modelAndView.addObject("transfer", transferDto);
//        transferService.saveTransfert(transferDto);
        return modelAndView;
    }

    @PostMapping("/transaction")
    public String sentAmount(TransferDto transfer, String email ,BigDecimal amount) {

        transferService.saveTransfert(transfer, amount);
        return "redirect:/transfer";
    }




}
