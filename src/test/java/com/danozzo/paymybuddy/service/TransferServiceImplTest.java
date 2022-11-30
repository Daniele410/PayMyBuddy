package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.Profit;
import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.ProfitRepository;
import com.danozzo.paymybuddy.repository.TransferRepository;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.TransferDto;
import exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    @InjectMocks
    TransferServiceImpl transferService;

    @Mock
    TransferRepository transferRepository;

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


    @Test
    void saveTransfert_test_shouldReturnTransferBalance() throws UserNotFoundException {
        //Given
        User debitAccount = new User(1L, "Jimmy", "Sax", "rossi@gmail.com", "12345");
        User creditAccount = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        debitAccount.setBalance(1000);
        creditAccount.setBalance(500);


        Profit profitApp = new Profit();
        profitApp.setId(1L);
        profitApp.setFees(100);
        TransferDto transferDto = new TransferDto("Gift", 100, creditAccount.getEmail());
        Transfer transfer = new Transfer(transferDto.getDescription(), transferDto.getAmount(), debitAccount, creditAccount);

        when(profitRepository.findById(1L)).thenReturn(Optional.of(profitApp));

        Authentication authentication = new UsernamePasswordAuthenticationToken(debitAccount.getEmail(), debitAccount.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        String name = authentication.getName();
        when(userService.getCurrentUser(name)).thenReturn(debitAccount);

        when(userService.findByEmail(anyString())).thenReturn(creditAccount);

        List<User> friends = new ArrayList<>();
        friends.add(creditAccount);
        debitAccount.setFriends(friends);

        when(profitRepository.save(profitApp)).thenReturn(profitApp);
        when(userRepository.save(debitAccount)).thenReturn(debitAccount);
        when(userRepository.save(creditAccount)).thenReturn(creditAccount);


        //When
        transferService.saveTransfert(transferDto, 100);

        //Then
        assertEquals(creditAccount.getBalance(), 600);
        assertEquals(debitAccount.getBalance(),
                1000 - transferDto.getAmount() - 0.5 * 100 / transferDto.getAmount());


    }

    @Test
    void saveTransfert_test_shouldReturnException() throws UserNotFoundException {
        //Given
        User debitAccount = new User(1L, "Jimmy", "Sax", "rossi@gmail.com", "12345");
        User creditAccount = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        debitAccount.setBalance(500);
        creditAccount.setBalance(1000);


        Profit profitApp = new Profit();
        profitApp.setId(1L);
        profitApp.setFees(100);
        TransferDto transferDto = new TransferDto("Gift", 100, creditAccount.getEmail());
        Transfer transfer = new Transfer(transferDto.getDescription(), transferDto.getAmount(), debitAccount, creditAccount);
        when(profitRepository.findById(1L)).thenReturn(Optional.of(profitApp));

        Authentication authentication = new UsernamePasswordAuthenticationToken(debitAccount.getEmail(), debitAccount.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        String name = authentication.getName();
        when(userService.getCurrentUser(name)).thenReturn(debitAccount);
        when(userService.findByEmail(anyString())).thenReturn(creditAccount);

        List<User> friends = new ArrayList<>();
        friends.add(creditAccount);
        debitAccount.setFriends(friends);

        //When
        UserNotFoundException result = assertThrows(UserNotFoundException.class,
                () -> transferService.saveTransfert(transferDto, 1000));

        //Then
        assertEquals("Not enough money on your account", result.getMessage());

    }

}