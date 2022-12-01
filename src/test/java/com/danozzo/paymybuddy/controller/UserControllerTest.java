package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.service.UserServiceImpl;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserController userController;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    User user;


    @Test
    void authenticated() {
    }

    @Test
    void getUserById() throws Exception {



//        mockMvc.perform(MockMvcRequestBuilders.get("/id").contentType(MediaType.APPLICATION_JSON)
//                .param("firstName","lastName","email","password")).andExpect(status().isOk());
    }

    @Test
    void userRegistrationDto() {
    }

    @Test
    void showFriends() {
    }

    @Test
    void showAddContactForm() {
    }

    @Test
    void addContact() {
    }

    @Test
    void registerContactFriend() throws Exception {

//        //Given
//        UserRegistrationDto user = new UserRegistrationDto("Frank", "Palumbo", "palumbo@mail.com", "12345");
//
//        //When
//        when(userService.getUserById(anyLong())).thenReturn(listUser.stream().findFirst());
//        //Then
//        mockMvc.perform(get("/id")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(model().size(3))
//                .andExpect((ResultMatcher) jsonPath("$.data['user'].firstName").value(user.getFirstName()))
//                .andExpect((ResultMatcher) jsonPath("$.data['user'].lastName").value(user.getLastName()))
//                .andExpect((ResultMatcher) jsonPath("$.data['account'].mail").value(user.getEmail()));

    }

    @Test
    void deleteFriend() {
    }

    @Test
    void bankRegistrationDto() {
    }

    @Test
    void showSendToTheBank() {
    }

    @Test
    void sentBankAmount() {
    }
}