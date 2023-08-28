package final_backend.Member.service;

import final_backend.Coupon.repository.CouponRepository;
import final_backend.Member.model.User;
import final_backend.Member.model.UserCredentialResponse;
import final_backend.Member.model.UserUpdateRequest;
import final_backend.Member.repository.UserRepository;
import final_backend.Utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Map<String, Boolean> checkNickNameExists(String nickName) {
        Map<String, Boolean> response = new HashMap<>();

        if (StringUtils.isEmpty(nickName)) {
            response.put("error", null);
            return response;
        }

        boolean exists = userRepository.existsByNickName(nickName);
        response.put("exists", exists);
        return response;
    }

    @Override
    public User updateUser(UserUpdateRequest request) {
        // 중복 닉네임 체크
        if(userRepository.existsByNickName(request.getNickName())) {
            throw new IllegalArgumentException("NickName already exists");
        }

        // 이메일로 유저 찾기
        User user = userRepository.findByEmail(request.getEmail());
        if(user == null){
            throw new IllegalArgumentException("그런 유저는 없습니다.");
        }

        // 유저 정보 업데이트
        user.setUserName(request.getUserName());
        user.setNickName(request.getNickName());

        return userRepository.save(user);
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

    public boolean updateNickName(String oldNickname, String newNickname) {
        User user = userRepository.findByNickName(oldNickname);

        if (user != null) {
            user.setNickName(newNickname);
            userRepository.save(user); // DB에 저장
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User updateLoverInfo(User user) {
        return userRepository.save(user);
    }


}