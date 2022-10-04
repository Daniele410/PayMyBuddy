package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.Role;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;


	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(), registrationDto.getPassword(), Arrays.asList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}

	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}

    public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
    }

	public Optional<User> findByUserName(String firstName){
		return userRepository.findByFirstName(firstName);
	}

	public Optional<User> findByEmail(String email){
		return userRepository.findByEmail(email);
	}

}
