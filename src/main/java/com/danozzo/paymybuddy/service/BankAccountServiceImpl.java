package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.BankAccountRepository;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.TransferDto;
import exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements IBankAccountService {
    private static final Logger logger = LogManager.getLogger("BankAccountServiceImpl");
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    IUserService userService;


    @Override
    @Transactional
    public BankAccount saveBank(BankRegistrationDto bankRegistrationDto, String emailConnectedUser) {
//        String userConnected = SecurityContextHolder.getContext().getAuthentication().getName();
//        User userConnected = userRepository.getUser(SecurityContextHolder);
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        User userConnected = userRepository.findByEmail(user.getName());

        BankAccount bankUser = new BankAccount(bankRegistrationDto.getBankName(), bankRegistrationDto.getIban(),
                bankRegistrationDto.getLocation());

        Optional<BankAccount> isAlreadyBank = userConnected.getBankAccountList()
                .stream()
                .filter(bank -> bank.getIban().equals(bankUser.getIban())).findAny();

        if (isAlreadyBank.isPresent()) {
            throw new RuntimeException("This bank is already present in this list");
        } else {
            userConnected.addBankAccount(bankUser);
            logger.info("saving bank account");
            userRepository.save(userConnected);
            return bankUser;
        }
    }

    @Override
    public List<BankAccount> getUsersBanks(String emailConnectedUser) {
        User connectedUser = userRepository.findByEmail(emailConnectedUser);
        return connectedUser.getBankAccountList();
    }


    public boolean existsByIban(String iban) {
        return bankAccountRepository.existsByIban(iban);
    }

    public void deleteBankById(Long id){
        bankAccountRepository.deleteById(id);
    }

    @Override
    public void saveBankTransfert(BankRegistrationDto bankAccountDto, double amount) throws Exception {
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();
        User debitAccount = userService.getCurrentUser(emailConnectedUser.getName());
        Optional<BankAccount> creditAccount = bankAccountRepository.findById(bankAccountDto.getUserId());

        List<BankAccount> banks = debitAccount.getBankAccountList();

        double amountWithCommission = amount + (5 * 100 / amount);
        double commission = amount* (5/100);

        double balanceDebitAccount = debitAccount.getBalance();
        double balanceCreditAccount = creditAccount.get().getBalance();

        if (balanceDebitAccount < amountWithCommission) {
            throw new Exception("Not enough money on your account");
        }
        debitAccount.setBalance(balanceDebitAccount - amountWithCommission);
        userRepository.save(debitAccount);

        creditAccount.get().setBalance(balanceCreditAccount + amount);
        bankAccountRepository.save(creditAccount.get());


    }


}
