package final_backend.Member.service;

import final_backend.Member.model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();
    User findByNickName(String name);

    User findByEmail(String email);

    User findByUid(Long uid);

    boolean validateByEmail(String email);

    boolean deleteUser(Long userId);

    User updateUser(Long userId, User updatedUser);

    String findByProviderName(String providerName);

    String login(String email, String nickName, String password) throws IllegalAccessException;

    String refresh(String email, String nickName, String s);

    UserCredentialResponse validateUser(String email, String password);
//    String login(String nickName, String email, String password);

    User updateUserInfo(String nickName, String profileImageUrl) throws Exception;

    boolean isNicknameTaken(String newNickname);

    boolean updateNickName(String nickName, String newNickname);

    User updateLoverInfo(User user);

    Map<String, Boolean> checkNickNameExists(String nickName);

    User updateUser(UserUpdateRequest request);

    public List<User> findAllByEmail(String email);

    public User updateUserRoleAndVipTime(String nickName, String itemName);

    public UserClickResponse handleClick(UserClickRequest request) throws Exception;

    public void resetClickCounts();


}