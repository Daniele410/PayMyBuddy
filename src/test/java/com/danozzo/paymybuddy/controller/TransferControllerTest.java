package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.TransferRepository;
import com.danozzo.paymybuddy.service.TransferServiceImpl;
import com.danozzo.paymybuddy.service.UserServiceImpl;
import com.danozzo.paymybuddy.web.dto.FriendDto;
import com.danozzo.paymybuddy.web.dto.TransferDto;
import exception.UserNotFoundException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Unit tests of TransferController
 */
@ExtendWith(MockitoExtension.class)
class TransferControllerTest {

    @InjectMocks
    TransferController transferController;

    @Mock
    UserServiceImpl userService;

    @Mock
    TransferServiceImpl transferService;

    @Mock
    TransferRepository transferRepository;

    @Mock
    SecurityContext securityContext;

    @Mock
    Model model;

    @Mock
    ModelAttribute modelAttribute;


    @Test
    void friendDto() {
        //Given-When-Then
        FriendDto user = new FriendDto();
        transferController.friendDto();

    }

    @Test
    void showReceivedPaymentsPageShouldReturnModifiedModelAndView() {

        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        TransferDto transferDto = new TransferDto();


        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        List<User> friends = List.of(user);

        //When
        ModelAndView result = transferController.showReceivedPaymentsPage(transferDto);
        result.addObject("friends", friends);

        //Then
        assertEquals(friends, result.getModel().get("friends"));


    }


    @Test
    void sentAmountShouldReturnRedirectPage() throws UserNotFoundException {

        //Given
        TransferDto transferDto = new TransferDto();
        String email = "palumbo@gmail.com";

        //When
        String result = transferController.sentAmount(transferDto, email, 100);

        //Then
        assertEquals("redirect:/transfer", result);

    }
}