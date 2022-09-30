package com.danozzo.paymybuddy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.danozzo.paymybuddy.service.BankAccountService;
import com.danozzo.paymybuddy.service.UserServiceImpl;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	BankAccountService bankAccountService;
	
	
	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {		
		/*Iterable<User> users = userService.getUsers();
		users.forEach(user -> System.out.println(user.getFirstName() +" "+ user.getLastName()));
		
		Iterable<BankAccount> bankAccounts = bankAccountService.getBankAccounts();
		bankAccounts.forEach(bankAccount -> System.out.println(bankAccount.getBankName()));*/
		
		}

}
