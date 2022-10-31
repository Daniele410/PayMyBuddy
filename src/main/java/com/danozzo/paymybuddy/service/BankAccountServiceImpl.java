package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.BankAccountRepository;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements IBankAccountService {
    private static final Logger logger = LogManager.getLogger("BankAccountServiceImpl");
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public BankAccount saveBank(BankRegistrationDto bankRegistrationDto, String emailConnectedUser) {
//        String userConnected = SecurityContextHolder.getContext().getAuthentication().getName();
//        User userConnected = userRepository.getUser(SecurityContextHolder);
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        User userConnected = userRepository.findByEmail(user.getName());

        BankAccount bankUser = new BankAccount(bankRegistrationDto.getBankName(), bankRegistrationDto.getIban(),
                bankRegistrationDto.getLocation(), userConnected.getId());

        Optional<BankAccount> isAlreadyBank = userConnected.getBankAccountList()
                .stream()
                .filter(bank -> bank.getIban().equals(bankUser.getIban())).findFirst();

        if (isAlreadyBank.isPresent()) {
            throw new RuntimeException("This bank is already present in this list");
        } else {

            List<BankAccount> listBankUser = userConnected.getBankAccountList();
            listBankUser.add(bankUser);
            userConnected.setBankAccountList(listBankUser);
            logger.info("saving bank account");
            userRepository.save(userConnected);
            return bankAccountRepository.save(bankUser);
        }
    }

    @Override
    public List<BankAccount> getUsersBanks(String emailConnectedUser) {
        User connectedUser = userRepository.findByEmail(emailConnectedUser);
        return connectedUser.getBankAccountList();
    }
//    @Override
//    public BankAccount save(BankRegistrationDto bankRegistrationDto) {
//        BankAccount bankAccount = new BankAccount(bankRegistrationDto.getBankName(),bankRegistrationDto.getIban(),
//                bankRegistrationDto.getLocation());
//        return bankAccountRepository.save(bankAccount);
//    }

    public boolean existsByIban(String iban) {
        return bankAccountRepository.existsByIban(iban);
    }

}
