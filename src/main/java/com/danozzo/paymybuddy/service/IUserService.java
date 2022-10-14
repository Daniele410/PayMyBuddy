package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface IUserService extends UserDetailsService {

    public User save(UserRegistrationDto registrationDto, String newPassword);


    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;


    public Object getUsers();

    Optional<User> getUserById(Integer id);

    User findByEmail(String email);

    Boolean existsByEmail(String email);

    void saveFriend(String email, String name);
}
