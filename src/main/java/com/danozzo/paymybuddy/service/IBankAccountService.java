package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;

import java.util.List;

public interface IBankAccountService {

    BankAccount saveBank(BankRegistrationDto bankRegistrationDto, String emailConnectedUser);

    public boolean existsByIban(String iban);

    List<BankAccount> getUsersBanks(String emailConnectedUser);

    public void deleteBankById(int id);
}
