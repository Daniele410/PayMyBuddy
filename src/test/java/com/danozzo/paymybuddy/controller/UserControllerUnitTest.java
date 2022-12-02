package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserServiceImpl userService;

    @Mock
    SecurityContext securityContext;


    @Test
    void showSendToTheBankShouldReturnModifiedModelAndView() {
        //GIVEN
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        String name = authentication.getName();
        BankRegistrationDto dto = new BankRegistrationDto();
        dto.setLocation("Paris");
        BankAccount account = new BankAccount();
        account.setBalance(2D);
        List<BankAccount> bankAccountList = List.of(account);
        when(userService.getUsersBanks(anyString())).thenReturn(bankAccountList);
        //WHEN
        ModelAndView result = controller.showSendToTheBank(dto);
        //THEN
        assertEquals(bankAccountList, result.getModel().get("bankAccountList"));
    }

//    @Test
//    void sentBankAmountShouldReturnModifiedModelAndView() throws Exception {
//        //GIVEN
//        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
//        user.setBalance(500);
//        BankRegistrationDto bankAccount = new BankRegistrationDto("IBM", "123456789", "Paris");
//        bankAccount.setBalance(1000);
//        user.getBankAccountList().add(bankAccount);
//
//       when(userService.saveUserTransfert(bankAccount,100)).thenReturn(any());
//        //WHEN
//        String result = controller.sentBankAmount(bankAccount, 100);
//        //THEN
//
//    }


}
