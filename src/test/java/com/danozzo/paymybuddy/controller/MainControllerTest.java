package com.danozzo.paymybuddy.controller;

import com.danozzo.paymybuddy.service.IUserService;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MainController.class)
@EnableWebMvc
class MainControllerTest {
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

    @MockBean
    ModelAttribute modelAttribute;

    @BeforeEach
    void setup() {
        userRegistrationDto = new UserRegistrationDto("Bryan", "Marvel", "marvel@gamil.com", "12345");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    void login() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andDo(print())
                .andExpect(view().name("login"));

    }

//    @Test
//    void home() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/"))
//                .andDo(print())
//                .andExpect(view().name("index"));
//    }
}