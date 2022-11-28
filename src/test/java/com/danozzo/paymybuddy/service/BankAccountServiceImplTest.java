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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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
    void saveBank() {
        //Given
//
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        User user1 = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
//        Authentication user = SecurityContextHolder.getContext().getAuthentication();
//        User userConnected = (User) when(userRepository.findByEmail(user.getName())).thenReturn(any());
//
//        BankAccount bankAccount = new BankAccount("IBM", "123456789", "Paris");
//
//
//        BankRegistrationDto bankDto = new BankRegistrationDto();
//        bankDto.setBankName("IBM");
//        bankDto.setIban("123456789");
//        bankDto.setLocation("Paris");
//
//        Optional<BankAccount> isAlreadyBank = userConnected.getBankAccountList()
//                .stream()
//                .filter(bank -> bank.getIban().equals(bankDto.getIban())).findAny();
//
//
//        when(isAlreadyBank.isPresent()).thenReturn(false);
//
//        when(userRepository.findByEmail(anyString())).thenReturn(user1);
//
//
//        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);
//
//        //When
//        bankAccountService.saveBank(bankDto, user.getName());
//
//        //Then
//        verify(bankAccountRepository, times(1)).save(bankCaptor.capture());
//        BankAccount bankResult = bankCaptor.getValue();
//        assertEquals("IBM", bankResult.getBankName());


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
    void saveBankTransfert_Test() throws Exception {
//        //Given
//        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
//        user.setBalance(1000);
//        BankRegistrationDto bankAccount = new BankRegistrationDto("IBM", "123456789", "Paris");
//        Profit profitApp= new Profit(1L, 100);
//
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//
//
//        when(userService.getCurrentUser(anyString())).thenReturn();
//
//        when(profitRepository.findById(anyLong())).thenReturn(Optional.of(profitApp));
//
//        when(profitRepository.save(any())).thenReturn(profitApp);
//        when(userRepository.save(any())).thenReturn(user);
//        when(bankAccountRepository.save(any())).thenReturn(bankAccount);
//
////        when(authentication.getName()).thenReturn(user.getEmail());
////        when(securityContext.getAuthentication().getPrincipal()).thenReturn(user);
//
//        when(userRepository.findByEmail(anyString())).thenReturn(user);
//
//        //When
//        bankAccountService.saveBankTransfert(bankAccount, 500);
//
//        //Then
//        assertEquals(500, user.getBalance());


    }
}