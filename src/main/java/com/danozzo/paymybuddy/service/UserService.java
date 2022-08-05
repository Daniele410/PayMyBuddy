package com.danozzo.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}
}
