package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import nl.altindag.log.LogCaptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Captor
    ArgumentCaptor<User> userCaptor;

    Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private static LogCaptor logcaptor;


    User user;
    User user2;
    User friend;

    private List<User> userList = new ArrayList<>();


//    @BeforeEach
//    void initialize() {
//        user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
//        user2 = new User("Toto", "Tata", "toto@mail.com", "12345");
//        friend = new User("Paulo", "Rossi", "rossi@gmail.com", "12345");
//    }


    @Test
    void saveUser_Test() {
        //Given
        UserRegistrationDto userDto = new UserRegistrationDto();
        userDto.setFirstName("toto");
        when(userRepository.save(any(User.class))).thenReturn(user);

        //When
        userService.save(userDto, "toto");

        //Then
        verify(userRepository, Mockito.times(1)).save(userCaptor.capture());
        User userResult = userCaptor.getValue();
        assertEquals("toto", userResult.getFirstName());
    }

    @Test
    void loadUserByUsername_Test_ShouldReturnTrue() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        //When
        userService.loadUserByUsername(user.getEmail());

        //Then
        verify(userRepository, Mockito.times(1)).findByEmail(user.getEmail());
        assertEquals("Frank", user.getFirstName());

    }

    @Test
    void loadUserByUsername_Test_shouldReturnException() throws UsernameNotFoundException {
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("Invalid username and password."));


    }

    @Test
    void getUserById_Test_ShouldReturnUser() {
        //Given
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(1L)).thenReturn(optionalUser);

        //When
        Optional resultUser = userService.getUserById(1L);

        //Then
        assertEquals(optionalUser, resultUser);
        verify(userRepository, Mockito.times(1)).findById(1L);
        assertEquals("Frank", user.getFirstName());
    }

    @Test
    void findByEmail_test_shouldReturnUser() {
        //Given
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        userList.add(user);
        when(userRepository.findByEmail(any())).thenReturn(user);

        //When
        User resultUser = userService.findByEmail("palumbo@mail.com");

        //Then
        assertEquals(user, resultUser);
        verify(userRepository, Mockito.times(1)).findByEmail(any());
        assertEquals("palumbo@mail.com", user.getEmail());
    }


    @Test
    void existsByEmail_test_shouldReturnTrue() {
        //Given
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        userList.add(user);
        when(userRepository.existsByEmail("palumbo@mail.com")).thenReturn(true);

        //When
        Boolean resultUser = userService.existsByEmail(user.getEmail());

        //Then
        verify(userRepository, Mockito.times(1)).existsByEmail(any());
        assertTrue(resultUser);

    }


    @Test
    void saveFriend_test() {
//      User connectedUser = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
//      User friend = new User("Toto", "Tata", "toto@mail.com", "12345");


    }

    @Test
    void getCurrentUser() {
    }

    @Test
    void getUsersFriends() {
    }

    @Test
    void getReceivedPayments() {
    }

    @Test
    void getSentPayment() {
    }

    @Test
    void findAll() {
    }

    @Test
    void deleteUserFriendById() {
    }
}