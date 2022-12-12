package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.Profit;
import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.BankAccountRepository;
import com.danozzo.paymybuddy.repository.ProfitRepository;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.TransferDto;
import exception.BankNotFoundException;
import exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
/**
 * BankAccountService contain all business service methods for BankAccount
 */
@Service
public class BankAccountServiceImpl implements IBankAccountService {
    private static final Logger logger = LogManager.getLogger("BankAccountServiceImpl");
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    IUserService userService;

    @Autowired
    ProfitRepository profitRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BankAccount saveBank(BankRegistrationDto bankRegistrationDto, String emailConnectedUser) throws RuntimeException {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        User userConnected = userRepository.findByEmail(user.getName());

        BankAccount bankUser = new BankAccount(bankRegistrationDto.getBankName(), bankRegistrationDto.getIban(),
                bankRegistrationDto.getLocation());

        Optional<BankAccount> isAlreadyBank = userConnected.getBankAccountList()
                .stream()
                .filter(bank -> bank.getIban().equals(bankUser.getIban())).findAny();

        if (isAlreadyBank.isPresent()) {
            throw new RuntimeException("This bank is already present in this list");
        } else {
            userConnected.addBankAccount(bankUser);
            logger.info("saving bank account");
            userRepository.save(userConnected);
            return bankUser;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BankAccount> getUsersBanks(String emailConnectedUser) {
        User connectedUser = userRepository.findByEmail(emailConnectedUser);
        return connectedUser.getBankAccountList();
    }

    /**
     * search the database if the bank exist sends a bulean value
     */
    public boolean existsByIban(String iban) {
        return bankAccountRepository.existsByIban(iban);
    }

    /**
     * delete bank by id
     */
    public void deleteBankById(Long id) {
        bankAccountRepository.deleteById(id);
    }

    /**
     * send money from user to bank
     */
    @Transactional
    public void saveBankTransfert(BankRegistrationDto bankAccountDto, double amount) throws UserNotFoundException, BankNotFoundException {
        Authentication emailConnectedUser = SecurityContextHolder.getContext().getAuthentication();
        User account = userService.getCurrentUser(emailConnectedUser.getName());

        Profit appProfit = profitRepository.findById(1L).get();

        Optional<BankAccount> isAlreadyBank = account.getBankAccountList()
                .stream()
                .filter(bank -> bank.getBankName().equals(bankAccountDto.getBankName())).findFirst();
        if (isAlreadyBank.isPresent()) {

            double amountWithCommission = amount + 0.005 * 100 / amount;
            double commission = amount * 0.005 / 100;

            double balanceAccount = account.getBalance();
            double balanceCreditAccount = isAlreadyBank.get().getBalance();

            if (balanceAccount < amountWithCommission) {
                throw new UserNotFoundException("Not enough money on your account");
            } else
                appProfit.setFees(appProfit.getFees() + commission);
            profitRepository.save(appProfit);

            account.setBalance(balanceAccount - amountWithCommission);
            userRepository.save(account);

            isAlreadyBank.get().setBalance(balanceCreditAccount + amount);
            bankAccountRepository.save(isAlreadyBank.get());

        }else {
            throw new BankNotFoundException("Bank not present");
        }
    }

}
