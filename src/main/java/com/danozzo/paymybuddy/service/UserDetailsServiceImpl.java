package com.danozzo.paymybuddy.service;


import com.danozzo.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.ArrayList;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    com.danozzo.paymybuddy.model.User userAccount;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        userAccount =userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));

      return new User(userAccount.getEmail(),userAccount.getPassword(),new ArrayList<>());
    }
}
