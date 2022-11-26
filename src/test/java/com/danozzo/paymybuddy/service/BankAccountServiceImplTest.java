package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.BankAccountRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @InjectMocks
    BankAccountServiceImpl bankAccountService;

    @Mock
    BankAccountRepository bankAccountRepository;

    @Mock
    UserRepository userRepository;

    Authentication authentication = Mockito.mock(Authentication.class);

    SecurityContext securityContext = Mockito.mock(SecurityContext.class);

    @Captor
    ArgumentCaptor<BankAccount> bankCaptor;

    BankAccount bankAccount;


    @Test
    void saveBank() {
//        //When
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        Authentication userConnected = SecurityContextHolder.getContext().getAuthentication();
//
//        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
//
//        BankRegistrationDto bankDto = new BankRegistrationDto();
//        bankDto.setBankName("IBM");
//        bankDto.setIban("123456789");
//        bankDto.setLocation("Paris");
//
//        BankAccount bankUser = new BankAccount(bankDto.getBankName(), bankDto.getIban(),
//                bankDto.getLocation());
//
//        when(userRepository.findByEmail(anyString())).thenReturn(user);
//
////        Optional<BankAccount> isAlreadyBank = userConnected.getBankAccountList()
////                .stream()
////                .filter(bank -> bank.getIban().equals(bankDto.getIban())).findAny();
//
//
//        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);
//
//
//        bankAccountService.saveBank(bankDto, userConnected.getName());
//
//        //Then
//        verify(bankAccountRepository, Mockito.times(1)).save(bankCaptor.capture());
//        BankAccount bankResult = bankCaptor.getValue();
//        assertEquals("toto", bankResult.getBankName());
//

    }

    @Test
    void getUsersBanks_Test_ShouldReturnIbanBank() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        BankAccount bankAccount = new BankAccount("IBM","123456789","Paris");

        when(userRepository.findByEmail("palumbo@mail.com")).thenReturn(user);
        user.getBankAccountList().add(bankAccount);


        //When
        List<BankAccount> userBanks = bankAccountService.getUsersBanks(user.getEmail());

        //Then
        verify(userRepository, Mockito.times(1)).findByEmail(user.getEmail());
        assertEquals(userBanks.get(0).getIban(), bankAccount.getIban());


    }

    @Test
    void existsByIban() {
    }

    @Test
    void deleteBankById() {
    }

    @Test
    void saveBankTransfert() {
    }
}