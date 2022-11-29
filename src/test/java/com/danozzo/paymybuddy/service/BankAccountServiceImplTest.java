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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @InjectMocks
    BankAccountServiceImpl bankAccountService;

    @Mock
    BankAccountRepository bankAccountRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    ProfitRepository profitRepository;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @Captor
    ArgumentCaptor<BankAccount> bankCaptor;

    BankAccount bankAccount;


    @Test
    void saveBank_test_shouldReturnSaveBankAccount() {
        //Given
        User userConnected = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        BankRegistrationDto bankAccount = new BankRegistrationDto("IBM", "123456789", "Paris");
        BankRegistrationDto bankAccount2 = new BankRegistrationDto("MyCredit", "987654321", "Paris");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userConnected.getEmail(), userConnected.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        String name = authentication.getName();
        when(userRepository.findByEmail(anyString())).thenReturn(userConnected);

        Optional<BankAccount> isAlreadyBank = userConnected.getBankAccountList()
                .stream()
                .filter(bank -> bank.getIban().equals(bankAccount2.getIban())).findAny();
        userConnected.addBankAccount(bankAccount);
        when(userRepository.save(any())).thenReturn(userConnected);

        //When
        bankAccountService.saveBank(bankAccount2, userConnected.getEmail());

        //Then

      assertTrue(userConnected.getBankAccountList().contains(bankAccount));


    }

    @Test
    void saveBank_test_shouldReturnRuntimeException() throws RuntimeException{
        //Given
        User userConnected = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        BankRegistrationDto bankAccount = new BankRegistrationDto("IBM", "123456789", "Paris");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userConnected.getEmail(), userConnected.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        String name = authentication.getName();
        when(userRepository.findByEmail(anyString())).thenReturn(userConnected);

        Optional<BankAccount> isAlreadyBank = userConnected.getBankAccountList()
                .stream()
                .filter(bank -> bank.getIban().equals(bankAccount.getIban())).findAny();
        userConnected.addBankAccount(bankAccount);

        //When


        RuntimeException result = assertThrows(RuntimeException.class,
                () -> bankAccountService.saveBank(bankAccount,userConnected.getEmail()));

        //Then
        assertEquals("This bank is already present in this list", result.getMessage());



    }

    @Test
    void getUsersBanks_Test_ShouldReturnIbanBank() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        BankAccount bankAccount = new BankAccount("IBM", "123456789", "Paris");

        when(userRepository.findByEmail("palumbo@mail.com")).thenReturn(user);
        user.getBankAccountList().add(bankAccount);


        //When
        List<BankAccount> userBanks = bankAccountService.getUsersBanks(user.getEmail());

        //Then
        verify(userRepository, Mockito.times(1)).findByEmail(user.getEmail());
        assertEquals(userBanks.get(0).getIban(), bankAccount.getIban());


    }

    @Test
    void existsByIban_ShouldReturnTrue() {

        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        BankAccount bankAccount = new BankAccount("IBM", "123456789", "Paris");
        when(bankAccountRepository.existsByIban(anyString())).thenReturn(true);
        user.getBankAccountList().add(bankAccount);

        //When
        Boolean result = bankAccountService.existsByIban(bankAccount.getIban());

        //Then
        verify(bankAccountRepository, Mockito.times(1)).existsByIban(anyString());
        assertTrue(result);


    }

    @Test
    void deleteBankById() {
        //Given
        BankAccount bankAccount = new BankAccount("IBM", "123456789", "Paris");
        bankAccount.setIdCountBank(1L);
        doNothing().when(bankAccountRepository).deleteById(bankAccount.getIdCountBank());

        //When
        bankAccountService.deleteBankById(bankAccount.getIdCountBank());

        //Then
        verify(bankAccountRepository).deleteById(bankAccount.getIdCountBank());


    }

    @Test
    void saveBankTransfert_test_shouldReturnBalance() throws Exception {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        user.setBalance(1000);
        BankRegistrationDto bankAccount = new BankRegistrationDto("IBM", "123456789", "Paris");
        bankAccount.setBalance(500);
        user.getBankAccountList().add(bankAccount);
        Profit profitApp = new Profit(1L, 100);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        String name = authentication.getName();

        when(userService.getCurrentUser(name)).thenReturn(user);
        when(profitRepository.findById(anyLong())).thenReturn(Optional.of(profitApp));
        Optional<BankAccount> isAlreadyBank = user.getBankAccountList().stream().findFirst();
        when(profitRepository.save(any())).thenReturn(profitApp);
        when(userRepository.save(any())).thenReturn(user);
        when(bankAccountRepository.save(any())).thenReturn(bankAccount);

        //When
        bankAccountService.saveBankTransfert(bankAccount, 500);

        //Then

        verify(profitRepository, Mockito.times(1)).save(profitApp);
        verify(userRepository, Mockito.times(1)).save(user);
        verify(bankAccountRepository, Mockito.times(1)).save(bankAccount);
        assertEquals(500, bankAccount.getBalance() - 500);


    }

    @Test
    void saveBankTransfert_test_shouldReturnUserNotFoundException() throws UserNotFoundException {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        user.setBalance(300);
        BankRegistrationDto bankAccount = new BankRegistrationDto("IBM", "123456789", "Paris");
        bankAccount.setBalance(500);
        user.getBankAccountList().add(bankAccount);
        Profit profitApp = new Profit(1L, 100);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        String name = authentication.getName();

        when(userService.getCurrentUser(name)).thenReturn(user);
        when(profitRepository.findById(anyLong())).thenReturn(Optional.of(profitApp));
        Optional<BankAccount> isAlreadyBank = user.getBankAccountList().stream().findFirst();

        //When
        UserNotFoundException result = assertThrows(UserNotFoundException.class,
                () -> bankAccountService.saveBankTransfert(bankAccount, 500));

        //Then
        assertEquals("Not enough money on your account", result.getMessage());

    }

}