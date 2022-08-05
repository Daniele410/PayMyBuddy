package com.danozzo.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danozzo.paymybuddy.model.Role;
import com.danozzo.paymybuddy.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	public Iterable<Role> getRoles(){
		return roleRepository.findAll();
	}
	
}
