package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.Profit;
import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.ProfitRepository;
import com.danozzo.paymybuddy.repository.TransferRepository;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.web.dto.TransferDto;
import exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferServiceImpl implements ITransferService {
    private static final Logger logger = LogManager.getLogger("TransferServiceImpl");
    @Autowired
    TransferRepository transferRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ProfitRepository profitRepository;



    @Transactional
    public Transfer saveTransfert(TransferDto transferDto, double amount) throws UserNotFoundException {
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();
        User debitAccount = userService.getCurrentUser(emailConnectedUser.getName());
        User creditAccount = userService.findByEmail(transferDto.getEmail());


        Profit appProfit = profitRepository.findAll().stream().findFirst().get();

        List<User> friends = debitAccount.getFriends();
        logger.debug("Contact list: " + friends);

        double amountWithCommission = amount + (5 * 100 / amount);
        double commission = amount * 5 / 100;

        double balanceDebitAccount = debitAccount.getBalance();
        double balanceCreditAccount = creditAccount.getBalance();

        if (balanceDebitAccount < amountWithCommission) {
            throw new UserNotFoundException("Not enough money on your account");
        }

        appProfit.setFees(appProfit.getFees() + commission);
        profitRepository.save(appProfit);

        debitAccount.setBalance(balanceDebitAccount - amountWithCommission);
        userRepository.save(debitAccount);

        creditAccount.setBalance(balanceCreditAccount + amount);
        userRepository.save(creditAccount);

        Transfer transfer = new Transfer(transferDto.getDescription(), transferDto.getAmount(),
                debitAccount, creditAccount);

        return transferRepository.save(transfer);
    }


}
