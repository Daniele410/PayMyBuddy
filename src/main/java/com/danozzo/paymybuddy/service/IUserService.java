package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;

import java.util.Optional;

public interface IUserService {

    public User save(UserRegistrationDto registrationDto);

    public Iterable<User> getUsers();

    public Optional<User> getUserById(Integer id);

    public Optional<User> findByEmail(String email);



}
