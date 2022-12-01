package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.security.SecurityConfig;
import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.service.UserServiceImpl;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@EnableWebMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;
    UserRegistrationDto userRegistrationDto;

    @MockBean
    Authentication authentication;

    @MockBean
    SecurityContext securityContext;

    @MockBean
    ModelAttribute modelAttribute;


    @BeforeEach
    void setup() {
        userRegistrationDto = new UserRegistrationDto("Bob", "Marvel", "marvel@gamil.com", "12345");
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity()).build();
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        String name = authentication.getName();
    }

    User user;


    @Test
    @WithMockUser(roles = "admin")
    void authenticated() throws Exception {



//        mockMvc.perform( get("/authenticated").secure( true ) ).andExpect( status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/authenticated").secure(true)
                        .param("principal.username", authentication.getName())).andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("authenticated"));


    }

//    @Test
//    void getUserById() throws Exception {
//
//
//
////        mockMvc.perform(MockMvcRequestBuilders.get("/id").contentType(MediaType.APPLICATION_JSON)
////                .param("firstName","lastName","email","password")).andExpect(status().isOk());
//    }
//
//    @Test
//    void userRegistrationDto() {
//    }
//
//    @Test
//    void showFriends() {
//    }
//
//    @Test
//    void showAddContactForm() {
//    }
//
//    @Test
//    void addContact() {
//    }
//
//    @Test
//    void registerContactFriend() throws Exception {
//
////        //Given
////        UserRegistrationDto user = new UserRegistrationDto("Frank", "Palumbo", "palumbo@mail.com", "12345");
////
////        //When
////        when(userService.getUserById(anyLong())).thenReturn(listUser.stream().findFirst());
////        //Then
////        mockMvc.perform(get("/id")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .accept(MediaType.APPLICATION_JSON))
////                .andExpect(model().size(3))
////                .andExpect((ResultMatcher) jsonPath("$.data['user'].firstName").value(user.getFirstName()))
////                .andExpect((ResultMatcher) jsonPath("$.data['user'].lastName").value(user.getLastName()))
////                .andExpect((ResultMatcher) jsonPath("$.data['account'].mail").value(user.getEmail()));
//
//    }
//
//    @Test
//    void deleteFriend() {
//    }
//
//    @Test
//    void bankRegistrationDto() {
//    }
//
//    @Test
//    void showSendToTheBank() {
//    }
//
//    @Test
//    void sentBankAmount() {
//    }
}