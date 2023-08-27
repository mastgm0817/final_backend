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
import java.util.List;
import java.util.Map;
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

    @PostMapping("/users/join")
    public ResponseEntity<TokenResponse> joinUser(@RequestBody UserJoinRequest userJoinRequest) {
        User existingUser = userService.findByEmail(userJoinRequest.getEmail());
        // 이미 가입된 회원일 때
        if (existingUser != null) { // 사용자가 존재하는지 확인
            return new ResponseEntity<>(new TokenResponse("User with the provided email already exists."), HttpStatus.OK);
        }
        else{
            User newUser = userJoinRequest.toUser(userJoinRequest.getProviderName()); // 신규 유저 객체 생성
            User createdUser = userService.createUser(newUser);
            Long couponId = couponService.createJoinCoupon();
            couponService.assignCouponToUser(couponId, createdUser.getUid());
            String token = userService.login(newUser.getNickName(), newUser.getEmail(), ""); // 토큰 생성
            return new ResponseEntity<>(new TokenResponse(token), HttpStatus.CREATED);
        }
    }
    @PostMapping("/users/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest dto) {
        User existingUser = userService.findByEmail(dto.getEmail());
        System.out.println(dto.getEmail());

        if (existingUser != null) { // 사용자가 존재
            System.out.println("컨트롤러 지나감");
            String accessToken = userService.login(dto.getEmail(), dto.getNickName(), "");
            return ResponseEntity.ok().body(new TokenResponse(accessToken));
        } else {
            // 사용자를 찾을 수 없는 경우, 적절한 상태 코드와 메시지를 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new TokenResponse("회원이 아닌 유저입니다."));
        }
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
    @PatchMapping("/users/info/update/{nickName}")
    public ResponseEntity<Object> updateNickName(@PathVariable String nickName,
                                                 @RequestBody Map<String, String> request,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        String newNickname = request.get("newNickname");
        User currentUser = userService.findByNickName(nickName);
        // 중복 닉네임 경우 예외 처리
        if ( userService.isNicknameTaken(newNickname)) {
            return ResponseEntity.badRequest().body(new ApiResponse("Nickname already taken: " + newNickname));
        }
        // 중복되지 않은 경우 닉네임 업데이트
        User updateUser = userService.updateNickName(nickName, newNickname);
        return ResponseEntity.ok(new ApiResponse("Nickname updated successfully: " + newNickname));
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
    @PostMapping("users/info/updateProfileImage/{nickName}")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile file,
                                                     @PathVariable("nickName") String nickName) {
        try {
            // 엽로드 된 파일 url 가져온다.
            String newFileUrl = s3Service.uploadFileToS3(file);
            // 기존 파일의 url 가져온다.
            User user = userService.findByNickName(nickName);
            String oldFileUrl = user.getProfileImage();
            // 기존 파일 삭제 ( 프로필 이미지 없을 경우 무시 )
            if (oldFileUrl != null) {
                s3Service.deleteFileFromS3(oldFileUrl);
            }
            // 사용자 정보 업데이트 ( 새 프로필 이미지 전달해 업데이트 )
            userService.updateUserInfo(nickName, newFileUrl);
            return ResponseEntity.ok(newFileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}