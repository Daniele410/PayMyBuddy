package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserRegistrationController.class)
@WebAppConfiguration
class UserRegistrationControllerTest {

    @MockBean
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

//    @Test
//    public void givenWac_whenServletContext_thenItProvidesGreetController() {
//        ServletContext servletContext = webApplicationContext.getServletContext();
//
//        Assert.assertNotNull(servletContext);
//        Assert.assertTrue(servletContext instanceof MockServletContext);
//        Assert.assertNotNull(webApplicationContext.getBean("greetController"));
//    }




    @Test
    void userRegistrationDto() {
        
    }

    @Test
    void showRegistrationForm() {
    }

    @Test
    void registerUserAccount()throws Exception {
                //Given

        UserRegistrationDto user = new UserRegistrationDto("Frank", "Palumbo", "palumbo@mail.com", "12345");
        String cryptedPassword = passwordEncoder.encode(user.getPassword());
        //When
        when(userService.save(user,cryptedPassword)).thenReturn(new User());
        //Then


        mockMvc.perform(post("redirect:/registration?success")
                .contentType(MediaType.APPLICATION_JSON).content



                .andDo(print()).andExpect(status().isOk())
                .andExpect(model().size(3))
                .andExpect(jsonPath("$.data['user'].firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.data['user'].lastName").value(user.getLastName()))
                .andExpect( jsonPath("$.data['account'].mail").value(user.getEmail()));


    }
    }
}