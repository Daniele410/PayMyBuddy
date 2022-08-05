package com.danozzo.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.repository.BankAccountRepository;

@Service
public class BankAccountService {
	
	@Autowired
	BankAccountRepository bankAccountRepository;

	public Iterable<BankAccount> getBankAccounts(){
		return bankAccountRepository.findAll();
	}
}
