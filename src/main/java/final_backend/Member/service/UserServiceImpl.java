package final_backend.Member.service;

import final_backend.Coupon.model.Coupon;
import final_backend.Coupon.repository.CouponRepository;
import final_backend.Member.model.User;
import final_backend.Member.model.UserCredentialResponse;
import final_backend.Member.repository.UserRepository;
import final_backend.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //환경 변수 가져오기

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User findByNickName(String name) {
        User user = userRepository.findByNickName(name);

        if (user != null) {
            User userDTO = new User();
            userDTO.setUid(user.getUid());
            userDTO.setNickName(user.getNickName());
            userDTO.setEmail(user.getEmail());
            userDTO.setUserRole(user.getUserRole());
            userDTO.setCouponList(user.getCouponList());
            userDTO.setBlackListDetails(user.getBlackListDetails());
            userDTO.setLover(user.getLover());

            return userDTO;
        } else {
            return null; // 또는 예외 처리를 하거나 다른 처리 방법을 선택
        }
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUid(Long uid) {
        return userRepository.findByUid(uid);
    }

    @Override
    public boolean validateByEmail(String email) {

        return userRepository.findByEmail(email) != null;
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        User user = userRepository.findByUid(userId);
        if (user != null) {
            couponRepository.deleteByUserId(user.getUid());
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
    private Long expiredMs = 1000 * 60 * 24 * 60l;
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

    @Override
    public User updateLoverInfo(User user) {
        return userRepository.save(user);
    }


}