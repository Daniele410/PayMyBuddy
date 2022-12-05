package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.service.UserServiceImpl;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

        mockMvc.perform(MockMvcRequestBuilders.get("/authenticated").secure(true)
                        .param("principal.username", authentication.getName())).andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("authenticated"));


    }

    @Test
    @WithMockUser(roles = "admin")
    void showFriends() throws Exception {
        User user = new User(1L, "Jimmy", "Sax", "rossi@gmail.com", "12345");
        User user2 = new User(2L, "Frank", "Palumbo", "palumbo@mail.com", "12345");

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);

        mockMvc.perform(MockMvcRequestBuilders.get("/contact").secure(true)
                        .param("principal.username", authentication.getName())).andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("contact"));

    }

    @Test
    @WithMockUser(roles = "admin")
    void registerContactFriend() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/addContact").secure(true)
                        .param("principal.username", authentication.getName()))
                .andDo(print())
                .andExpect(view().name("addContact")
                );

    }

    @Test
    void showSendToTheBank() throws Exception {
        BankAccount bankAccount = new BankAccount("IBM", "123456789", "Paris");
        BankRegistrationDto bankRegistrationDto = new BankRegistrationDto("Credit", "987654321", "Rome");

        List<BankAccount> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount);
        when(userService.getUsersBanks(any())).thenReturn(bankAccountList);

        ModelAndView expectedReturn = new ModelAndView();
        expectedReturn.addObject("bankAccountList", bankAccountList);
        expectedReturn.addObject("bankAccount", bankRegistrationDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/userTransfer") .contentType(MediaType.APPLICATION_JSON)
                        .param("bankAccountList", String.valueOf(bankAccountList))
                        .param("user.bankName",bankAccount.getBankName())
                        .secure(true)
                        .param("principal.username", authentication.getName()));


    }

}