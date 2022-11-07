package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.web.dto.FriendDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TransactionController {
    private static final Logger logger = LogManager.getLogger("TransactionController");

    @Autowired
    private IUserService userService;


//    @GetMapping("transaction")
//    public String showTransactionPage() {
//        return "transaction";
//    }

    @ModelAttribute("transaction")
    public FriendDto friendDto() {
        return new FriendDto();
    }

    @GetMapping("/transaction")
    public ModelAndView showFriendsToSend() {
        ModelAndView modelAndView = new ModelAndView("transaction");
        String emailConnectedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        List<User> listFriends = userService.getUsersFriends(emailConnectedUser);
        logger.info(listFriends);
        modelAndView.addObject("listFriends", listFriends);
        return modelAndView;

    }

}
