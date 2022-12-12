package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import exception.UserEmailNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * contain all business service methods for user
 */
public interface IUserService extends UserDetailsService {

    /**
     * Save in DataBase New User whit password
     */
    public User save(UserRegistrationDto registrationDto, String newPassword);

    /**
     * @param username
     * @return load user by userName
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * @param id
     * @return get user by id
     */
    Optional<User> getUserById(Long id);

    /**
     * @param email
     * @return find user by email
     */
    User findByEmail(String email);

    /**
     * @param email
     * @return true if is done
     * @return check if the email exists
     */
    Boolean existsByEmail(String email);


    /**
     * @param emailConnectedUser
     * @param email              save friend in dataBase
     * @throws RuntimeException
     * @throws IllegalArgumentException
     */
    void saveFriend(String emailConnectedUser, String email) throws RuntimeException, IllegalArgumentException;

    /**
     * @param bankAccountDto
     * @param amount
     * save transfer bank to user
     * @throws Exception
     */
    public void saveUserTransfert(BankRegistrationDto bankAccountDto, double amount) throws Exception;


    /**
     * @param emailConnectedUser get current user connected
     * @return
     */
    User getCurrentUser(String emailConnectedUser);


    /**
     * @param emailConnectedUser get all user friends
     * @return
     */
    public List<User> getUsersFriends(String emailConnectedUser);

    /**
     * @param emailConnectedUser get list all received Payment of current user
     * @return
     */
    public Set<Transfer> getReceivedPayments(String emailConnectedUser);

    /**
     * @param emailConnectedUser get list all sent Payment of current user
     * @return
     */
    public Set<Transfer> getSentPayment(String emailConnectedUser);

    /**
     * @return list of all user
     */
    List<User> findAll();


    /**
     * @param id delete user friend by id
     */
    void deleteUserFriendById(Long id);


    /**
     * @param emailConnectedUser
     * @return get all banks of current user
     */
    public List<BankAccount> getUsersBanks(String emailConnectedUser);

}
