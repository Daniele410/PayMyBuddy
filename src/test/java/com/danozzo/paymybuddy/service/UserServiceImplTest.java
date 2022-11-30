package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.model.Profit;
import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.BankAccountRepository;
import com.danozzo.paymybuddy.repository.ProfitRepository;
import com.danozzo.paymybuddy.repository.UserRepository;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.UserRegistrationDto;
import exception.BankNotFoundException;
import exception.UserNotFoundException;
import nl.altindag.log.LogCaptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @Mock
    ProfitRepository profitRepository;

    @Mock
    BankAccountRepository bankAccountRepository;


    @Captor
    ArgumentCaptor<User> userCaptor;


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
        //Given
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        User user2 = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");

        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(userRepository.findByEmail(anyString())).thenReturn(user).thenReturn(user2);


        //When
        IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
                () -> userService.saveFriend(user.getEmail(), user2.getEmail()));
        //Then
        assertEquals("Your account not is user friend!", result.getMessage());
    }

    @Test
    void saveFriend_test_shouldReturnSaveFriend() throws RuntimeException {
        User user = new User(1L, "Jimmy", "Sax", "rossi@gmail.com", "12345");
        User user2 = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);
        //Given
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(userRepository.findByEmail(anyString())).thenReturn(user).thenReturn(user2);

        Optional<User> isAlreadyFriend = userList.stream()
                .filter(u -> u.getEmail().equals(user2.getEmail())).findFirst();

        userService.saveFriend("palumbo@mail.com", "rossi@gmail.com");

        //Then
        verify(userRepository, Mockito.times(2)).findByEmail(any());

    }

    @Test
    void saveFriend_test_shouldReturnSaveFriendRuntimeException() throws RuntimeException {
        //Given
        User connectedUser = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        User friendUser = new User(1L, "Frank", "Palumbo", "coco@gmail.com", "12345");
        List<User> userFriend = new ArrayList<>();
        userFriend.add(friendUser);
        connectedUser.setFriends(userFriend);

        when(userRepository.findByEmail("palumbo@mail.com")).thenReturn(connectedUser);
        when(userRepository.findByEmail("coco@gmail.com")).thenReturn(friendUser);

        //When
        RuntimeException result = assertThrows(RuntimeException.class,
                () -> userService.saveFriend(friendUser.getEmail(), connectedUser.getEmail()));

        //Then
        assertEquals("This user is already in this list", result.getMessage());

    }

    @Test
    void saveFriend_test_shouldReturnSaveFriendRuntimeException_friendUserSameConnectedUser() throws RuntimeException {
        //Given
        User connectedUser = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        User friendUser = new User(1L, "Frank", "Palumbo", "coco@gmail.com", "12345");
        List<User> userFriend = new ArrayList<>();
        userFriend.add(friendUser);
        connectedUser.setFriends(userFriend);

        when(userRepository.findByEmail(anyString())).thenReturn(connectedUser);
        when(userRepository.findByEmail(anyString())).thenReturn(friendUser);

        //When
        RuntimeException result = assertThrows(RuntimeException.class,
                () -> userService.saveFriend(friendUser.getEmail(), friendUser.getEmail()));

        //Then
        assertEquals("Your account not is user friend!", result.getMessage());

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
    void findAllTest_ShouldReturnFirstName() {
        //Given
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");
        User user2 = new User("Toto", "Tata", "toto@mail.com", "12345");
        userList.add(user);
        userList.add(user2);
        when(userRepository.findAll()).thenReturn(userList);

        //When
        List<User> listUser = userService.findAll();

        //Then
        verify(userRepository, Mockito.times(1)).findAll();
        assertEquals("Frank", listUser.get(0).getFirstName());


    }

    @Test
    void deleteUserFriendById() {
        User user = new User(1L, "Frank", "Palumbo", "palumbo@mail.com", "12345");


        doNothing().when(userRepository).deleteById(user.getId());

        userService.deleteUserFriendById(1L);


        verify(userRepository, Mockito.times(1)).deleteById(user.getId());
        verify(userRepository).deleteById(user.getId());


    }

    /**
     * Send money from bankCredit to userCredit
     */
    @Test
    void saveUserTransfertBank_test_shouldReturnUserBalance() throws Exception {
        //Given

        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        user.setBalance(500);
        BankRegistrationDto bankAccount = new BankRegistrationDto("IBM", "123456789", "Paris");
        bankAccount.setBalance(1000);
        user.getBankAccountList().add(bankAccount);

        Profit profitApp = new Profit();
        profitApp.setId(1L);
        profitApp.setFees(100);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        String name = authentication.getName();

        when(userService.getCurrentUser(name)).thenReturn(user);
        when(profitRepository.findById(anyLong())).thenReturn(Optional.of(profitApp));
        Optional<BankAccount> isAlreadyBank = user.getBankAccountList().stream().findFirst();
        when(profitRepository.save(any())).thenReturn(profitApp);
        when(userRepository.save(user)).thenReturn(user);
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        //When
        userService.saveUserTransfert(bankAccount, 500);

        //Then

        verify(profitRepository, Mockito.times(1)).save(profitApp);
        verify(userRepository, Mockito.times(1)).save(user);
        verify(bankAccountRepository, Mockito.times(1)).save(bankAccount);
        assertEquals(1000, user.getBalance());

    }

    /**
     * Send money from bankCredit to userCredit return exception
     */
    @Test
    void saveUserTransfertBank_test_shouldReturnUserNotFoundException() throws UserNotFoundException {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        user.setBalance(1000);
        BankRegistrationDto bankAccount = new BankRegistrationDto("IBM", "123456789", "Paris");
        bankAccount.setBalance(500);
        user.getBankAccountList().add(bankAccount);

        Profit profitApp = new Profit();
        profitApp.setId(1L);
        profitApp.setFees(100);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        String name = authentication.getName();

        when(userService.getCurrentUser(name)).thenReturn(user);
        when(profitRepository.findById(anyLong())).thenReturn(Optional.of(profitApp));
        Optional<BankAccount> isAlreadyBank = user.getBankAccountList().stream().findFirst();

        //When
        UserNotFoundException result = assertThrows(UserNotFoundException.class,
                () -> userService.saveUserTransfert(bankAccount, 500));

        //Then
        assertEquals("Not enough money on your account", result.getMessage());

    }
    @Test
    void saveUserTransfertBank_test_isAlreadyBankNotPresentReturnException() throws BankNotFoundException {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        user.setBalance(1000);
        BankRegistrationDto bankAccount = new BankRegistrationDto("IBM", "123456789", "Paris");
        bankAccount.setBalance(500);

        Profit profitApp = new Profit();
        profitApp.setId(1L);
        profitApp.setFees(100);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        String name = authentication.getName();

        when(userService.getCurrentUser(name)).thenReturn(user);
        when(profitRepository.findById(anyLong())).thenReturn(Optional.of(profitApp));
        Optional<BankAccount> isAlreadyBank = user.getBankAccountList().stream().findFirst();

        //When
        BankNotFoundException result = assertThrows(BankNotFoundException.class,
                () -> userService.saveUserTransfert(bankAccount, 500));

        //Then
        assertEquals("Bank not present", result.getMessage());

    }


}