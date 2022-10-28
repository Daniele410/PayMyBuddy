package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.BankAccountRepository;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl implements IBankAccountService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UserRepository userRepository;



    @Override
    public BankAccount save(BankRegistrationDto bankRegistrationDto, String emailConnectedUser) {
//        String userConnected = SecurityContextHolder.getContext().getAuthentication().getName();
//        User userConnected = userRepository.getUser(SecurityContextHolder);
        User userConnected = userRepository.findByEmail(emailConnectedUser);

        BankAccount bankAccount = new BankAccount(bankRegistrationDto.getBankName(), bankRegistrationDto.getIban(),
                bankRegistrationDto.getLocation(), userConnected.getId());
        List<BankAccount> listBankUser = userConnected.getBankAccountList();
        listBankUser.add(bankAccount);
        userConnected.setBankAccountList(listBankUser);
        userRepository.save(userConnected);
        return bankAccountRepository.save(bankAccount);
    }
}
