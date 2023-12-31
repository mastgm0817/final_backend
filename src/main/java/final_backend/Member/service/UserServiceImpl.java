package final_backend.Member.service;

import final_backend.Coupon.model.Coupon;
import final_backend.Coupon.repository.CouponRepository;
import final_backend.Member.exception.BlockedUserException;
import final_backend.Member.exception.UserNotFoundException;
import final_backend.Member.model.*;
import final_backend.Member.repository.UserRepository;
import final_backend.Utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
            userDTO.setInquiryList(user.getInquiryList());
            userDTO.setUserName(user.getUserName());

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
        User user = userRepository.findByNickName(request.getUserName());

        if(user == null){
            throw new IllegalArgumentException("그런 유저는 없습니다.");
        }

        // 유저 정보 업데이트
        user.setUserName(request.getUserName());
        user.setNickName(request.getNickName());

        return userRepository.save(user);
    }

    @Override
    public List<User> findAllByEmail(String email) {
        return userRepository.findAllByEmail(email);
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
    public User updateUserInfo(String nickName, String profileImageUrl) throws Exception {
        User user = userRepository.findByNickName(nickName);
        if (user == null) {
            throw new Exception("User not found with nickname: " + nickName);
        }

        user.setProfileImageUrl(profileImageUrl);
        return userRepository.save(user); // 업데이트된 사용자 정보 반환
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


    public User updateUserRoleAndVipTime(String nickName, String itemName) {
        User user = userRepository.findByNickName(nickName);
        if ( user != null) {

            if (user.getIsBlocked() == true) {
                user.setIsBlocked(false);
            }

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime newVipEndTime = now;

            // 기존 VIP 만료 시간이 있다면 그것을 사용
            if (user.getVipEndTime() != null && user.getVipEndTime().isAfter(now)) {
                newVipEndTime = user.getVipEndTime();
            }

            switch (itemName) {
                case "한달 이용권":
                    newVipEndTime = newVipEndTime.plusMonths(1);
                    break;
                case "일주일 이용권":
                    newVipEndTime = newVipEndTime.plusWeeks(1);
                    break;
                case "일년 이용권":
                    newVipEndTime = newVipEndTime.plusYears(1);
                    break;
                default:
                    throw new IllegalArgumentException("알 수 없는 이용권입니다.");
            }

            user.setVipStartTime(now);
            user.setVipEndTime(newVipEndTime);
            user.setUserRole(UserRole.VIP);
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
    }

    public UserClickResponse handleClick(UserClickRequest request) throws Exception {
        User user = userRepository.findByNickName(request.getNickName());
        UserClickResponse response = new UserClickResponse();

        if (user == null) {
            throw new UserNotFoundException("유저를 찾을 수 없습니다.");
        }

        // VIP, ADMIN 등급 확인
        if ("VIP".equals(user.getUserRole().name()) || "ADMIN".equals(user.getUserRole().name()))
        {
            response.setMessage("무제한 이용권");
            return response;
        }
        // Normal일 경우
        else {
            // 마지막 클릭 시간과 클릭 카운트 업데이트
            user.setLastClickTime(LocalDateTime.now());
            user.setClickCount(user.getClickCount() + 1);
            userRepository.save(user);
            if (user.getClickCount() >= 3) {
                user.setIsBlocked(true);
                userRepository.save(user);
                response.setMessage("하루 이용 횟수를 다 소진하셨습니다.");
                response.setRemainingClicks(0);  // 남은 클릭 횟수를 0으로 설정
            } else {
                response.setRemainingClicks(3 - user.getClickCount());
            }
        }
        return response;

    }


    // 매일 자정에 클릭 횟수 초기화
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetClickCounts() {
        List<User> users = userRepository.findAllByUserRole("Normal");
        for (User user : users) {
            user.setClickCount(0);
            user.setIsBlocked(false);
            userRepository.save(user);
        }
    }
}