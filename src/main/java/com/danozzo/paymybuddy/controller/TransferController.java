package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.repository.TransferRepository;
import com.danozzo.paymybuddy.service.ITransferService;
import com.danozzo.paymybuddy.web.dto.UserTransactionDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class TransferController {
    private static final Logger logger = LogManager.getLogger("TransferController");

    @Autowired
    public ITransferService iTransferService;

    @Autowired
    TransferRepository transferRepository;



    @ModelAttribute("transfer")
    public UserTransactionDTO friendDto() {
        return new UserTransactionDTO();
    }


    @GetMapping("/transfer")
    public String showTransferPage() {
        return "transfer";
    }


}
