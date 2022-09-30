package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.User;

import java.util.Optional;

public interface IUserService {

    public Iterable<User> getUsers();

    public Optional<User> getUserById(Integer id);




}
