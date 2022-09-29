package com.danozzo.paymybuddy.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.service.UserService;

@RestController
public class UserController {
	private static final Logger logger = LogManager.getLogger("UserController");
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/user")
	public Iterable<User> getUsers() {
		logger.info("get all users");
		return userService.getUsers();}
	
	@GetMapping(value = "/id")
	public Optional<User> getUserById(Integer id){
		logger.info("get user by id");
		return userService.getUserById(id);}
	
	
	
}
