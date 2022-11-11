package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User save(UserRegistrationDto registrationDto, String newPassword) {
        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                newPassword);

        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username and password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        //
    }


    @Override
    public Optional<User> getUserById(Integer id) {
        return Optional.empty();
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveFriend(String email, String emailConnectedUser) {
        User friendUser = userRepository.findByEmail(email);
        User connectedUser = userRepository.findByEmail(emailConnectedUser);
        Optional<User> isAlreadyFriend = connectedUser.getFriends().stream().filter(friend -> friend.getEmail().equals(friendUser.getEmail())).findAny();
        if (friendUser != connectedUser && emailConnectedUser != email) {
            if (isAlreadyFriend.isPresent()) {
                throw new RuntimeException("This user is already in this list");
            }
            List<User> friendsList = connectedUser.getFriends();
            friendsList.add(friendUser);
            userRepository.save(connectedUser);

        } else {
            throw new IllegalArgumentException("Your account not is user friend!");
        }

    }


    @Override
    public List<User> getUsersFriends() {
        return null;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public User getCurrentUser(String emailConnectedUser) {
        User connectedUser = userRepository.findByEmail(emailConnectedUser);
        return connectedUser;
    }


    @Override
    public List<User> getUsersFriends(String emailConnectedUser) {
        User connectedUser = userRepository.findByEmail(emailConnectedUser);
        return connectedUser.getFriends();
    }

    @Override
    public Set<Transfer> getReceivedPayments(String emailConnectedUser) {
        User connectedUser = userRepository.findByEmail(emailConnectedUser);
        return connectedUser.getReceivedPayments();
    }

    @Override
    public Set<Transfer> getSentPayment(String emailConnectedUser) {
        User connectedUser = userRepository.findByEmail(emailConnectedUser);
        return connectedUser.getSentPayments();
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserFriendById(Long id) {

        userRepository.deleteById(id);
    }


}
