package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.service.UserServiceImpl;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    SecurityContext securityContext;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;




    @Test
    void showSendToTheBankShouldReturnModifiedModelAndView() {
        //GIVEN
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

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

        List<User> listFriends = List.of(user);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.existsByEmail(anyString())).thenReturn(true);

        //When
        String result = controller.registerContactFriend(registrationDto, bindingResult);
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

        //When
        String result = controller.registerContactFriend(registrationDto, bindingResult);
        //Then
        assertEquals("redirect:/addContact?error", result);

    }


    @Test
    void registerContactFriendReturnModifiedModelAndView() throws RuntimeException {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        User friend = new User("Jack", "Morgan", "jack@mail.com", "12345");
        friend.setId(1L);
        user.getFriends().add(friend);
        List<User> listFriends = List.of(user);


        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userService.getCurrentUser(user.getEmail())).thenReturn(user);

        when(userService.getUsersFriends(user.getEmail())).thenReturn(user.getFriends());
        when(userService.getCurrentUser(user.getEmail())).thenReturn(listFriends.stream().findFirst().get());
        when(userRepository.save(user)).thenReturn(user);

        //When
        String result = controller.deleteFriend(1L);
        //Then
        assertEquals("redirect:/contact", result);

    }

    @Test
    void registerContactFriendReturnException() throws RuntimeException {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        User friend = new User("Jack", "Morgan", "jack@mail.com", "12345");
        friend.setId(1L);
        user.getFriends().add(friend);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userService.getCurrentUser(user.getEmail())).thenReturn(user);

        when(userService.getUsersFriends(anyString())).thenReturn(user.getFriends());
        when(userService.getCurrentUser(user.getEmail())).thenReturn(user.getFriends().get(0));


        //When
        RuntimeException result = assertThrows(RuntimeException.class,
                () -> controller.deleteFriend(friend.getId()));

        //Then
        assertEquals("Id Not Found", result.getMessage());

    }

    @Test
    void showSendToTheBankReturnModifiedModelAndView() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        BankRegistrationDto bankAccount = new BankRegistrationDto("IBM", "123456789", "Paris");
        bankAccount.setBalance(1000);
        List<BankAccount> bankAccountList = List.of(bankAccount);
        when(userService.getUsersBanks(any())).thenReturn(bankAccountList);

        //When
        ModelAndView result = controller.showSendToTheBank(bankAccount);

        //Then
        assertEquals(bankAccountList, result.getModel().get("bankAccountList"));


    }



    @Test
    void showSendToTheBankAmountReturnModifiedModelAndView() throws Exception {
        //Given
        BankRegistrationDto bankAccount = new BankRegistrationDto("IBM", "123456789", "Paris");
        bankAccount.setBalance(1000);


        //When
        String result = controller.sentBankAmount(bankAccount, 100.0);

        //Then
        assertEquals("redirect:/userTransfer", result);


    }


}
