package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.service.BankAccountServiceImpl;
import com.danozzo.paymybuddy.service.UserServiceImpl;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit Tests of BankAccountController
 */
@ExtendWith(MockitoExtension.class)
class BankAccountControllerTest {

    @InjectMocks
    private BankAccountController bankController;

    @Mock
    BankAccountServiceImpl bankAccountService;

    @Mock
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    SecurityContext securityContext;

    @Mock
    Model model;

    @Mock
    ModelAttribute modelAttribute;

    @Mock
    BindingResult bindingResult;


    @Test
    void showBanksShouldReturnModifiedModelAndView() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        BankAccount bankAccount = new BankAccount("IBM", "123456789", "Paris");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        List<BankAccount> bankAccountList = List.of(bankAccount);


        //When
        ModelAndView result = bankController.showBanks();
        result.addObject("bankAccountList", bankAccountList);

        //Then
        assertEquals(bankAccountList, result.getModel().get("bankAccountList"));


    }

    @Test
    void bankRegistrationDto() {
        bankController.bankRegistrationDto();

    }

    @Test
    void showRegistrationForm() {
        //Given-When
        String result = bankController.showRegistrationForm();

        //Then
        assertEquals("bankRegistration", result);

    }

    @Test
    void registerBankUserShouldReturnBindingResultFalse() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        BankRegistrationDto bankAccountDto = new BankRegistrationDto("IBM", "123456789", "Paris");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(bindingResult.hasErrors()).thenReturn(true);
        //When
        String result = bankController.registerBankUser(bankAccountDto, bindingResult);
        //Then
        assertEquals("redirect:/bankRegistration?error", result);
    }

    @Test
    void registerBankUserShouldReturnModifiedModelAndView() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        BankRegistrationDto bankAccountDto = new BankRegistrationDto("IBM", "123456789", "Paris");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bankAccountService.existsByIban(anyString())).thenReturn(false);
        when(bankAccountService.saveBank(any(), anyString())).thenReturn(bankAccountDto);
        //When
        String result = bankController.registerBankUser(bankAccountDto, bindingResult);
        //Then
        assertEquals("redirect:/bankRegistration?success", result);
    }

    @Test
    void registerBankUserShouldReturnExistByIbanTrue() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        BankRegistrationDto bankAccountDto = new BankRegistrationDto("IBM", "123456789", "Paris");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bankAccountService.existsByIban(anyString())).thenReturn(true);

        //When
        String result = bankController.registerBankUser(bankAccountDto, bindingResult);
        //Then
        assertEquals("redirect:/bankRegistration?error", result);
    }

    @Test
    void deleteBankByIdShouldReturnDelete() {
        //Given
        BankAccount bank = new BankAccount();
        bank.setIdCountBank(1L);
        List<BankAccount> banks = List.of(bank);

        //When
        String result = bankController.deleteBankById(bank.getIdCountBank());

        //Then
        assertEquals("redirect:/bankAccount", result);

    }

    @Test
    void showSendToTheBankReturnModifiedModelAndView() {

        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        BankRegistrationDto bankAccountDto = new BankRegistrationDto("IBM", "123456789", "Paris");
        List<BankAccount> bankAccountList = List.of(bankAccountDto);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userService.getUsersBanks(anyString())).thenReturn(bankAccountList);

        //When
        ModelAndView result = bankController.showSendToTheBank(bankAccountDto);
        //Then
        assertEquals(bankAccountList, result.getModel().get("bankAccountList"));

    }

    @Test
    void sentBankAmountShouldReturnModifiedView() throws Exception {
        //Given
        BankRegistrationDto bankAccount = new BankRegistrationDto("IBM", "123456789", "Paris");
        bankAccount.setBalance(1000);


        //When
        String result = bankController.sentBankAmount(bankAccount, 100.0);

        //Then
        assertEquals("redirect:/bankTransfer", result);


    }
}