package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.TransferRepository;
import com.danozzo.paymybuddy.web.dto.TransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements ITransferService {

    @Autowired
    TransferRepository transferRepository;


    @Autowired
    UserServiceImpl userService;


    public Transfer saveTransfert(TransferDto transferDto) {
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();
        User debitAccount = userService.findByEmail(transferDto.getEmail());
        User creditAccount = userService.findByEmail(emailConnectedUser.getName());

        Transfer transfer = new Transfer(transferDto.getDescription(), transferDto.getAmount(),
                debitAccount,creditAccount);
        return transferRepository.save(transfer);
    }


}
