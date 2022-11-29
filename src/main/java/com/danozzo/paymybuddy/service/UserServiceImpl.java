package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.Profit;
import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.BankAccountRepository;
import com.danozzo.paymybuddy.repository.ProfitRepository;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ProfitRepository profitRepository;


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
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }


    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveFriend(String email, String emailConnectedUser) throws RuntimeException, IllegalArgumentException {
        User friendUser = userRepository.findByEmail(email);
        User connectedUser = userRepository.findByEmail(emailConnectedUser);

        Optional<User> isAlreadyFriend = connectedUser.getFriends().stream()
                .filter(friend -> friend.getEmail().equals(friendUser.getEmail())).findFirst();
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
    public User getCurrentUser(String emailConnectedUser) {
        User connectedUser = userRepository.findByEmail(emailConnectedUser);
        return connectedUser;
    }


    @Override
    public List<User> getUsersFriends(String emailConnectedUser) {
        User connectedUser = userRepository.findByEmail(emailConnectedUser);
        return connectedUser.getFriends();
    }

    public List<BankAccount> getUsersBanks(String emailConnectedUser){
        User connectedUser = userRepository.findByEmail(emailConnectedUser);
        return connectedUser.getBankAccountList();
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

    /**
     * send money from user credit to bank credit
     */
    @Override
    public void saveUserTransfert(BankRegistrationDto bankAccountDto, double amount) throws UserNotFoundException {
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();

        User account = userRepository.findByEmail(emailConnectedUser.getName());

        Profit appProfit = profitRepository.findById(1L).get();

        Optional<BankAccount> isAlreadyBank = account.getBankAccountList()
                .stream()
                .filter(bank -> bank.getBankName().equals(bankAccountDto.getBankName())).findFirst();
        if (isAlreadyBank.isPresent()) {

            double amountWithCommission = amount + 0.5 * 100 / amount;
            double commission = amount * 0.5 / 100;

            double balanceAccount = isAlreadyBank.get().getBalance();
            double balanceCreditAccount = account.getBalance();

            if (balanceAccount < amountWithCommission) {
                throw new UserNotFoundException("Not enough money on your account");
            } else
                appProfit.setFees(appProfit.getFees() + commission);
            profitRepository.save(appProfit);

            isAlreadyBank.get().setBalance(balanceAccount - amountWithCommission);
            bankAccountRepository.save(isAlreadyBank.get());

            account.setBalance(balanceCreditAccount + amount);
            userRepository.save(account);


    }

    }


}
