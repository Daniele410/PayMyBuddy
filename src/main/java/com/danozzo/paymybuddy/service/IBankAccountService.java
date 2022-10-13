package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;

public interface IBankAccountService {
    public BankAccount save(BankRegistrationDto bankRegistrationDto);
}
