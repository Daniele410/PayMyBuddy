package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @InjectMocks
    IBankAccountService bankAccountService;

    @Mock
    BankAccountRepository bankAccountRepository;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    void saveBank() {


    }

    @Test
    void getUsersBanks() {
    }

    @Test
    void existsByIban() {
    }

    @Test
    void deleteBankById() {
    }

    @Test
    void saveBankTransfert() {
    }
}