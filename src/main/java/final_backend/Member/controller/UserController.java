package final_backend.Member.controller;
import final_backend.Coupon.service.CouponService;
import final_backend.Member.exception.ApiResponse;
import final_backend.Member.model.*;
import final_backend.Member.service.S3Service;
import final_backend.Member.service.UserService;
import final_backend.Utils.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CouponService couponService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/users/delete/{nickname}")
    public ResponseEntity<Void> deleteUser(@PathVariable String nickname) {
        User foundUser = userService.findByNickName(nickname);
        Long num = foundUser.getUid();
        try {
            userService.deleteUser(num);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/users/{uid}")
    public ResponseEntity<User> updateUser(@PathVariable String uid, @RequestBody User updatedUser) {
        Long num = Long.valueOf(uid);
        User user = userService.updateUser(num, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/check/nickname")
    public Map<String, Boolean> checkNickName(@RequestParam String nickName) {
        return userService.checkNickNameExists(nickName);
    }

    @PostMapping("/users/join/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest updateRequest) {
        try {
            User updatedUser = userService.updateUser(updateRequest);
            return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/users/join")
//    public ResponseEntity<TokenResponse> joinUser(@RequestBody UserJoinRequest userJoinRequest) {
//        User existingUser = userService.findByEmail(userJoinRequest.getEmail());
//        // 이미 가입된 회원일 때
//        if (existingUser != null) { // 사용자가 존재하는지 확인
//            return new ResponseEntity<>(new TokenResponse("User with the provided email already exists."), HttpStatus.OK);
//        }
//        else{
//            User newUser = userJoinRequest.toUser(userJoinRequest.getProviderName()); // 신규 유저 객체 생성
//            User createdUser = userService.createUser(newUser);
//            Long couponId = couponService.createJoinCoupon();
//            couponService.assignCouponToUser(couponId, createdUser.getUid());
//            String token = userService.login(newUser.getNickName(), newUser.getEmail(), ""); // 토큰 생성
//            return new ResponseEntity<>(new TokenResponse(token), HttpStatus.CREATED);
//        }
//    }

    @PostMapping("/users/join")
    public ResponseEntity<TokenAndUserResponse> joinUser(@RequestBody UserJoinRequest userJoinRequest) throws IllegalAccessException {

        User existingUser = userService.findByEmail(userJoinRequest.getEmail());
        String providerName = userJoinRequest.getProviderName();
        if (existingUser != null && existingUser.getProviderName() == providerName) {
            return new ResponseEntity<>(new TokenAndUserResponse("User with the provided email already exists.", null), HttpStatus.OK);
        } else {
            User newUser = userJoinRequest.toUser(userJoinRequest.getProviderName());
            User createdUser = userService.createUser(newUser);
            Long couponId = couponService.createJoinCoupon();
            couponService.assignCouponToUser(couponId, createdUser.getUid());
            String token = userService.login(newUser.getNickName(), newUser.getEmail(), "");

            TokenAndUserResponse response = new TokenAndUserResponse(token, createdUser);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity<TokenAndNickNameResponse> login(@RequestBody UserLoginRequest dto) throws IllegalAccessException {
        List<User> existingUsers = userService.findAllByEmail(dto.getEmail());
        String providerName = dto.getProviderName();

        for (User existingUser : existingUsers) {
            if (existingUser.getProviderName().equals(providerName)) { // 문자열 비교는 '==' 대신 'equals()' 메서드를 사용
                String accessToken = userService.login(dto.getEmail(), dto.getNickName(), "");
                String nickName = existingUser.getNickName(); // 또는 어떤 방법으로든 닉네임을 가져옵니다.
                return ResponseEntity.ok().body(new TokenAndNickNameResponse(accessToken, nickName));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new TokenAndNickNameResponse("회원이 아닌 유저입니다.", null));
    }

    // 내 정보 받아오기
    @GetMapping("/users/info/{nickName}")
    public ResponseEntity<User> getUserInfo(@PathVariable("nickName") String nickName) throws UnsupportedEncodingException {
        User user = userService.findByNickName(nickName);
        if ( user != null ){
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // 내 정보 중복확인 후 수정

    @PostMapping("/users/info/updateNickname")
    public ResponseEntity<String> updateNickName(@RequestParam String oldNickname,
                                                 @RequestParam String newNickname) {
        boolean updateStatus = userService.updateNickName(oldNickname, newNickname);

        if (updateStatus) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 연인 정보 검색 후 불러오기
    @GetMapping("/users/info/search/{inputnickName}")
    public ResponseEntity<User> getUserInfoByNickName(@PathVariable String inputnickName) {
        User user = userService.findByNickName(inputnickName); // 닉네임으로 유저 정보 검색
        if (user != null) {
            return ResponseEntity.ok(user); // 검색된 유저 정보 반환
        } else {
            return ResponseEntity.notFound().build(); // 유저 정보가 없으면 404 반환
        }
    }
    // 검색된 유저 등록
    @PostMapping("/users/savelover/{nickName}")
    public ResponseEntity<User> saveLoverInfo(@RequestBody LoverInfoRequest loverInfoRequest,
                                              @PathVariable("nickName") String nickName) {
        try {
            // 현재 로그인 한 유저의 정보
            User user = userService.findByNickName(nickName);

            // 연인이 없는지 확인 후 유저의 연인 정보 업데이트
            if (user != null && user.getLover() == null) {
                user.setLover(loverInfoRequest.getLoverNickName());
                userService.updateLoverInfo(user);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    // 연인과 헤어지기..
    @DeleteMapping("/users/deletelover/{nickName}")
    public ResponseEntity<User> deleteLoverInfo(@PathVariable("nickName") String nickName){
        try{
            // 현재 로그인 한 유저의 정보
            User user = userService.findByNickName(nickName);
            if ( user != null && user.getLover() != null ) {
                // 연인 정보 삭제
                user.setLover(null);
                userService.updateLoverInfo(user);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    // s3 연결
    private final S3Service s3Service;
    @Autowired
    public UserController(S3Service s3Service) {
        this.s3Service = s3Service;
    }
    @PostMapping("/users/info/updateProfileImage/{nickName}")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile file,
                                                     @PathVariable("nickName") String nickName) {
        try {
            // 파일을 S3에 업로드하고 새 파일 URL 가져오기
            String newFileUrl = s3Service.uploadFileToS3(file);
            return ResponseEntity.ok(newFileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/users/info/updateProfileImage/{nickName}")
    public ResponseEntity<User> updateProfileImage(@PathVariable("nickName") String nickName,
                                                   @RequestBody User user) {
        try {
            User loginedUser = userService.findByNickName(nickName);
            // 기존 파일 URL 가져오기
            String oldFileUrl = loginedUser.getProfileImage();

            // 기존 파일 삭제
            if (oldFileUrl != null && !oldFileUrl.equals(user.getProfileImage())) {
                s3Service.deleteFileFromS3(oldFileUrl);
            }

            // 사용자 정보 업데이트 ( 새 프로필 이미지 전달해 업데이트 )
            User updatedUser = userService.updateUserInfo(nickName, user.getProfileImage());
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}