package final_backend.Member.service;

import final_backend.Member.model.User;
import final_backend.Member.repository.UserRepository;
import final_backend.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //환경 변수 가져오기

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByNickName(String name) {
        return userRepository.findByNickName(name);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean deleteUser(Long userId) {
        User user = userRepository.findByUid(userId);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public User updateUser(Long userId, User updatedUser) {
        User user = userRepository.findByUid(userId);
        if (user != null) {
            user.setUserName(updatedUser.getUserName());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }
        return null;
    }
    public String findByProviderName(String providerName) {
        return userRepository.findByProviderName(providerName);
    }
    @Value("${jwt.secret}")
    private String secretKey;
    private Long expiredMs = 1000 * 60 * 60l;
    public String login(String userName, String password){
        return JwtUtil.createJwt(userName,secretKey, expiredMs);
    }
}
