package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.service.UserServiceImpl;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserServiceImpl userService;

    @Mock
    SecurityContext securityContext;

    @Mock
    Model model;

    @Mock
    ModelAttribute modelAttribute;

    @Mock
    BindingResult bindingResult;

//    @ModelAttribute("user")
//    public UserRegistrationDto userRegistrationDto() {
//        return new UserRegistrationDto();
//    }


    @Test
    void showSendToTheBankShouldReturnModifiedModelAndView() {
        //GIVEN
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
//        String name = authentication.getName();
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

    @Test
    void showFriendsShouldReturnModifiedModelAndView() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
//        String name = authentication.getName();
        List<User> listFriends = List.of(user);
        when(userService.getUsersFriends(anyString())).thenReturn(listFriends);

        //When
        ModelAndView result = controller.showFriends();

        //Then
        assertEquals(listFriends, result.getModel().get("listFriends"));


    }

    @Test
    void addContactShouldReturnModifiedModelAndView() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<User> listFriends = List.of(user);


        when(userService.getUsersFriends(anyString())).thenReturn(listFriends);
        model.addAttribute("user", user);


        //When

        String result = controller.addContact(model);
        //Then
        assertEquals("addContact", result);


    }

    @Test
    void registerContactFriendReturnModifiedModelAndViewSuccess() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("morgan@gmail.com");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.existsByEmail(anyString())).thenReturn(true);
//        when(modelAttribute.name()).thenReturn("User");

        //When

        String result = controller.registerContactFriend(registrationDto,bindingResult);
        //Then
        assertEquals("redirect:/addContact?success", result);


    }

    @Test
    void registerContactFriendReturnError() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("morgan@gmail.com");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.existsByEmail(anyString())).thenReturn(false);
//        when(modelAttribute.name()).thenReturn("User");

        //When

        String result = controller.registerContactFriend(registrationDto,bindingResult);
        //Then
        assertEquals("redirect:/addContact?error", result);


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
