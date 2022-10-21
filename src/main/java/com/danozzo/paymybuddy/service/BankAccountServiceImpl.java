package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.BankAccountRepository;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BankAccountServiceImpl implements IBankAccountService{

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount save(BankRegistrationDto bankRegistrationDto) {

        BankAccount bankAccount = new BankAccount(bankRegistrationDto.getBankName(),bankRegistrationDto.getIban(),
                bankRegistrationDto.getLocation());
        return bankAccountRepository.save(bankAccount);
    }
}
