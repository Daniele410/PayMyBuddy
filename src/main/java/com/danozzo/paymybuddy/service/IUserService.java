package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserService extends UserDetailsService {

    public User save(UserRegistrationDto registrationDto, String newPassword);


    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;



    Optional<User> getUserById(Integer id);

    User findByEmail(String email);

    Boolean existsByEmail(String email);

    void saveFriend(String emailConnectedUser ,String email);


//    List<User> getUsersFriends();




    User getCurrentUser(String emailConnectedUser);

    public List<User> getUsersFriends(String emailConnectedUser);

    public Set<Transfer> getReceivedPayments(String emailConnectedUser);

    public Set<Transfer> getSentPayment(String emailConnectedUser);

    List<User> findAll();


    void deleteUserFriendById(Long id);


}
