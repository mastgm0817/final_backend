package final_backend.Member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import final_backend.Member.model.User;
import final_backend.Member.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByUserId(String userId) {
        return userRepository.findByUid(userId);
    }

    public boolean deleteUser(String userId) {
        User user = userRepository.findByUid(userId);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public User updateUser(String userId, User updatedUser) {
        User user = userRepository.findByUid(userId);
        if (user != null) {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }
        return null;
    }
}
