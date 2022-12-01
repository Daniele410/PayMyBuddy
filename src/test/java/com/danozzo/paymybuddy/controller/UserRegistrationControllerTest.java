package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static java.nio.file.Paths.get;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserRegistrationController.class)
@EnableWebMvc
class UserRegistrationControllerTest {

    @MockBean
    private IUserService userService;

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private WebApplicationContext webApplicationContext;

//    @Autowired
//    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;
    UserRegistrationDto userRegistrationDto;

    @BeforeEach
    void setup() {
        userRegistrationDto = new UserRegistrationDto("Bryan", "Marvel", "marvel@gamil.com", "12345");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @MockBean
    ModelAttribute modelAttribute;



//    @BeforeEach
//    public void init() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .apply(springSecurity())
//                .build();
//    }



//    @Test
//    void userRegistrationDto() {
//
//    }

    @Test
    void showRegistrationForm() throws Exception {
       mockMvc.perform(MockMvcRequestBuilders.get("/registration"))
                .andDo(print())
                .andExpect(view().name("registration")
                );


    }

    @Test
    void registerUserAccount()throws Exception {

        UserRegistrationDto user =
                new UserRegistrationDto("Frank", "Palumbo", "palumbo@mail.com", "12345");
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        when(userService.save(user,encryptedPassword)).thenReturn(new User());



        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstName", "Frank")
                .param("lastName","Palumbo")
                .param("email", "palum@gmail.com")
                .param("password", "12345")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration?success"));


    }

}