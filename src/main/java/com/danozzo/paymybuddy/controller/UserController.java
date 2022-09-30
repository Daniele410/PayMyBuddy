package com.danozzo.paymybuddy.controller;

import java.util.Optional;

import com.danozzo.paymybuddy.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.service.UserServiceImpl;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	private static final Logger logger = LogManager.getLogger("UserController");

	@Autowired
	private IUserService userService;
	
	@GetMapping(value = "/users")
	public ModelAndView getUsers(Model model) {
		model.addAttribute("users", userService.getUsers());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("users");
		logger.info("get all users");
		return modelAndView;
	}
	
	@GetMapping(value = "/id")
	public Optional<User> getUserById(Integer id){
		logger.info("get user by id");
		return userService.getUserById(id);}
	
	
	
}
