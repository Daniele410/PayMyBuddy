package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.Transfer;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

        UsernameNotFoundException result = assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("Invalid username and password."));

        assertEquals(result.getMessage(), "Invalid username and password.");
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
    void saveFriend_test_shouldReturnIllegalArgumentException() throws IllegalArgumentException {
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        User user2 = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        //GIVEN
        //si on appelle la méthode findByEmail plusieurs fois ce sera toujours user
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        //Je veux deux user différents pour les deux appels
        when(userRepository.findByEmail(anyString())).thenReturn(user).thenReturn(user2);


        //WHEN
        IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
                () -> userService.saveFriend(user.getEmail(), user2.getEmail()));
        //THEN
        assertEquals("Your account not is user friend!", result.getMessage());
    }

    @Test
    void saveFriend_test_shouldReturnSaveFriend() throws RuntimeException{
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        User user2 = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);
        //Given
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(userRepository.findByEmail(anyString())).thenReturn(user).thenReturn(user2);

        Optional<User> isAlreadyFriend = userList.stream()
                .filter(u -> u.getEmail().equals(user2.getEmail())).findFirst();

        userService.saveFriend("palumbo@mail.com", "tutu");

        //Then
        verify(userRepository, Mockito.times(2)).findByEmail(any());

    }

    @Test
    void saveFriend_test_shouldReturnSaveFriendRuntimeException() throws RuntimeException{
//        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
//        User user2 = new User(1L, "Frank", "Palumbo", "coco@gmail.com", "12345");
//
//        List<User> userList = new ArrayList<>();
//        userList.add(user);
//        userList.add(user2);
//        //Given
//        when(userRepository.findByEmail(anyString())).thenReturn(user2);
//        when(userRepository.findByEmail(anyString())).thenReturn(user2).thenReturn(user2);
//        when(userRepository.save(user2)).thenReturn(user2);
//
////        Optional<User> isAlreadyFriend = userList.stream()
////                .filter(u -> u.getEmail().equals(user2.getEmail())).findFirst();
//
////        isAlreadyFriend.isPresent(user2.getEmail());
//
//        RuntimeException result = assertThrows(RuntimeException.class,
//                () -> userService.saveFriend("palumbo@mail.com", "coco@gmail.com"));
//
//        //Then
//        assertEquals("This user is already in this list", result.getMessage());
////
    }

    @Test
    void getCurrentUser() {
        //Given
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        List<User> listUser = new ArrayList<>();
        listUser.add(user);
        when(userRepository.findByEmail("palumbo@mail.com")).thenReturn(listUser.get(0));

        //When
        User resultUser = userService.getCurrentUser("palumbo@mail.com");

        //Then
        assertEquals(listUser.get(0), resultUser);
        verify(userRepository, Mockito.times(1)).findByEmail("palumbo@mail.com");
        assertEquals("Frank", user.getFirstName());


    }


    @Test
    void getUsersFriends_ShouldReturnFriendEmail() {

        //Given
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        User friend = new User("Toto", "Tata", "toto@mail.com", "12345");
        List<User> listFriend = new ArrayList<>();
        listFriend.add(friend);
        user.setFriends(listFriend);
        when(userRepository.findByEmail("palumbo@mail.com")).thenReturn(user);

        //When
        User resultUser = userService.getUsersFriends("palumbo@mail.com").get(0);

        //Then
        assertEquals(listFriend.get(0), resultUser);
        verify(userRepository, Mockito.times(1)).findByEmail("palumbo@mail.com");
        assertEquals(friend.getEmail(), user.getFriends().get(0).getEmail());

    }

    @Test
    void getUsersBanksTest_shouldReturnIbanBank() {
        //Given
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        BankAccount bankAccount = new BankAccount("IBM", "qwertyuiop", "Paris");
        List<BankAccount> listBanks = new ArrayList<>();
        listBanks.add(bankAccount);
        user.setBankAccountList(listBanks);
        when(userRepository.findByEmail("palumbo@mail.com")).thenReturn(user);

        //When
        BankAccount resultUser = userService.getUsersBanks("palumbo@mail.com").get(0);

        //Then
        assertEquals(listBanks.get(0), resultUser);
        verify(userRepository, Mockito.times(1)).findByEmail("palumbo@mail.com");
        assertEquals(bankAccount.getIban(), user.getBankAccountList().get(0).getIban());

    }


    @Test
    void getReceivedPayments() {
        //Given
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        Transfer transfer = new Transfer("Gift", 100.0);
        transfer.setCreditAccount(transfer.getCreditAccount());
        when(userRepository.findByEmail("palumbo@mail.com")).thenReturn(user);

        //When
        userService.getReceivedPayments(user.getEmail());

        //Then
        verify(userRepository, Mockito.times(1)).findByEmail("palumbo@mail.com");

    }

    @Test
    void getSentPayment() {
        //Given
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        Transfer transfer = new Transfer("Gift", 100.0);
        transfer.setCreditAccount(transfer.getCreditAccount());
        user.setSentPayments(Set.of(transfer));
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        //When
        Set<Transfer> result = userService.getSentPayment(user.getEmail());

        //Then
        assertNotNull(result);
        verify(userRepository, Mockito.times(1)).findByEmail("palumbo@mail.com");


    }

    @Test
    void findAll() {
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        User user2 = new User("Toto", "Tata", "toto@mail.com", "12345");
        userList.add(user);
        userList.add(user2);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> listUser = userService.findAll();

        verify(userRepository, Mockito.times(1)).findAll();
        assertEquals("Frank", listUser.get(0).getFirstName());


    }

    @Test
    void deleteUserFriendById() {
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");

//        Optional<User> optionalUser = Optional.of(user);
//        Optional<User> optUser = Optional.of(user);

        doNothing().when(userRepository).deleteById(user.getId());

       userService.deleteUserFriendById(1L);




        verify(userRepository, Mockito.times(1)).deleteById(user.getId());
        verify(userRepository).deleteById(user.getId());



    }
}