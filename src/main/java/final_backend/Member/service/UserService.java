package final_backend.Member.service;

import final_backend.Member.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    User findByNickName(String name);

    User findByEmail(String email);

    boolean deleteUser(Long userId);

    User updateUser(Long userId, User updatedUser);

    String findByProviderName(String providerName);

    String login(String email, String nickName, String password);

    String refresh(String email, String nickName, String s);

    User validateUser(String email, String password);
//    String login(String nickName, String email, String password);
}
