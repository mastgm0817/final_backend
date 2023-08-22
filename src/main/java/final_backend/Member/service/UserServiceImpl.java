package final_backend.Member.service;

import final_backend.Member.model.User;
import final_backend.Member.model.UserCredentialResponse;
import final_backend.Member.repository.UserRepository;
import final_backend.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //환경 변수 가져오기

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByNickName(String name) {
        return userRepository.findByNickName(name);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean deleteUser(Long userId) {
        User user = userRepository.findByUid(userId);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public User updateUser(Long userId, User updatedUser) {
        User user = userRepository.findByUid(userId);
        if (user != null) {
            user.setUserName(updatedUser.getUserName());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }
        return null;
    }

    public UserCredentialResponse validateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            UserCredentialResponse returnUser = user.toUser();
            System.out.println(returnUser);
            return returnUser; // 사용자 정보를 반환
        }
        return null; // 인증 실패 시 null 반환
    }



    @Override
    public String findByProviderName(String providerName) {
        return userRepository.findByProviderName(providerName);
    }
    @Value("${jwt.secret}")
    private String secretKey;
    private Long expiredMs = 1000 * 60 * 60l;
    @Override
    public String login(String email, String nickName, String password){
//        return JwtUtil.createJwt(nickName, email, secretKey, expiredMs);
        return JwtUtil.createJwt(email, nickName, secretKey, expiredMs);
    }

    @Override
    public String refresh(String email, String nickName, String password){
//        return JwtUtil.createJwt(nickName, email, secretKey, expiredMs);
        return JwtUtil.createRefreshJwt(email, nickName, secretKey, expiredMs);
    }

    // 프로필 이미지 수정 로직
    @Override
    public void updateUserInfo(String nickName, String profileImageUrl) {
        User user = userRepository.findByNickName(nickName);
        if (user != null) {
            user.setProfileImageUrl(profileImageUrl);
            userRepository.save(user);
        }
    }

    @Override
    public boolean isNicknameTaken(String nickName) {
        return userRepository.existsByNickName(nickName);
    }

    @Override
    public User updateNickName(String nickName, String newNickname) {
        User user = userRepository.findByNickName(nickName);

        // 중복된 닉네임인 경우 에외처리
        if ( userRepository.existsByNickName(newNickname)) {
            throw new IllegalArgumentException("Nickname already taken: " + newNickname);
        }

        user.setNickName(newNickname);
        return userRepository.save(user);
    }
}
