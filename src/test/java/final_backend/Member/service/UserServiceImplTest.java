//package final_backend.Member.service;
//
//import final_backend.Member.model.User;
//import final_backend.Member.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import static org.mockito.Mockito.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UserServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testCreateUser() {
//        User user = new User(); // Create a user object
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        User createdUser = userService.createUser(user);
//
//        assertNotNull(createdUser);
//        assertEquals(user, createdUser);
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        List<User> userList = new ArrayList<>(); // Create a list of users
//        when(userRepository.findAll()).thenReturn(userList);
//
//        List<User> result = userService.getAllUsers();
//
//        assertNotNull(result);
//        assertEquals(userList, result);
//    }
//}