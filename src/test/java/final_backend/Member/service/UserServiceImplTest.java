package final_backend.Member.service;

import final_backend.Member.model.User;
import final_backend.Member.repository.UserRepository;
import final_backend.Member.service.UserServiceImpl;
import final_backend.Utils.JwtUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser() {
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals(createdUser,user);

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllUsers() {
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);

        List<User> fetchedUsers = userService.getAllUsers();

        assertNotNull(fetchedUsers);
        assertEquals(userList, fetchedUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findByNickName() {
        String nickname = "testNickname";
        User user = new User();
        when(userRepository.findByNickName(nickname)).thenReturn(user);

        User fetchedUser = userService.findByNickName(nickname);

        assertNotNull(fetchedUser);
        assertEquals(user, fetchedUser);
        verify(userRepository, times(1)).findByNickName(nickname);
    }

    @Test
    void findByEmail() {
        String email = "test@example.com";
        User user = new User();
        when(userRepository.findByEmail(email)).thenReturn(user);

        User fetchedUser = userService.findByEmail(email);

        assertNotNull(fetchedUser);
        assertEquals(user, fetchedUser);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void deleteUser() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findByUid(userId)).thenReturn(user);

        boolean result = userService.deleteUser(userId);

        assertTrue(result);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void updateUser() {
        Long userId = 1L;
        User originalUser = new User();
        when(userRepository.findByUid(userId)).thenReturn(originalUser);

        User updatedUser = new User();
        updatedUser.setUserName("UpdatedUserName");
        updatedUser.setEmail("updated@example.com");

        User savedUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertNotNull(savedUser);
        assertEquals(savedUser, result);
        assertEquals(updatedUser.getUserName(), result.getUserName());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByUid(userId);
        verify(userRepository, times(1)).save(originalUser);
    }

    @Test
    void findByProviderName() {
        String providerName = "testProvider";
        when(userRepository.findByProviderName(providerName)).thenReturn(providerName);

        String result = userService.findByProviderName(providerName);

        assertNotNull(result);
        assertEquals(providerName, result);
        verify(userRepository, times(1)).findByProviderName(providerName);
    }

    @Test
    void login() {
        String userName = "testUser";
        String secretKey = "testSecretKey";
        Long expiredMs = 3600000L; // 1 hour in milliseconds
        when(userService.login(userName, secretKey)).thenCallRealMethod();

        String jwtToken = userService.login(userName, secretKey);

        assertNotNull(jwtToken);
        // Here, you can add assertions for the generated JWT token if needed.
    }
}