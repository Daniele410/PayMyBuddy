package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.TransferRepository;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.web.dto.TransferDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class TransferServiceImpl implements ITransferService {
    private static final Logger logger = LogManager.getLogger("TransferServiceImpl");
    @Autowired
    TransferRepository transferRepository;

    @Autowired
    UserRepository userRepository;


    @Autowired
    UserServiceImpl userService;

    @Transactional
    public Transfer saveTransfert(TransferDto transferDto, BigDecimal amount) {
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();
        User debitAccount = userService.findByEmail(emailConnectedUser.getName());
        User creditAccount = userService.findByEmail(transferDto.getEmail());

        BigDecimal senderNewAmount = debitAccount.getBalance().subtract(amount);
        BigDecimal receiverNewAmount = creditAccount.getBalance().add(amount);
//
//        userRepository.findById(debitAccount.getEmail(),senderNewAmount);
//        userRepository.findById(creditAccount.getEmail(),receiverNewAmount);


        Transfer transfer = new Transfer(transferDto.getDescription(), transferDto.getAmount(),
                debitAccount, creditAccount);


//        withdraw(debitAccount.getBalance(),creditAccount.getBalance());

//        if (transferDto.getAmount() < debitAccount.getBalance()){
//
//            throw new RuntimeException("Transfer fails!! you do not have enough money!");
//        }else{
//            transferDto.getAmount() -= debitAccount.getBalance();
//            creditAccount.getBalance() += transferDto.getAmount();
//            logger.info("User of " +  + " become €"+this.balance);



        return transferRepository.save(transfer);
    }


//    public void withdraw( double balanceCredit, TransferDto transferDto){
//        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();
//        User debitAccount = userService.findByEmail(transferDto.getEmail());
//        User creditAccount = userService.findByEmail(emailConnectedUser.getName());
//       double balanceDebit = debitAccount.getBalance();
//        double amountDebit = creditAccount.getBalance();
//
//        if (debitAccount.getBalance() < creditAccount.getBalance()){
//            balanceDebit -= balanceCredit;
//
//        }else {
//
//            throw new RuntimeException("error!! you do not have enough money!");
//        }
//    }

}
