package final_backend.Member.controller;


import final_backend.Member.model.*;
import final_backend.Member.service.UserService;
import final_backend.Utils.NomalTokenResponse;
import final_backend.Utils.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/normal/users")
public class NormalUserController {

    @Autowired
    private UserService userService;


    @PostMapping("/authorize")
    public ResponseEntity<?> checkEmailPasswd(@RequestBody UserCredentialRequest userCredentialRequest) {
        String email = userCredentialRequest.getEmail();
        String password = userCredentialRequest.getPassword();
        UserCredentialResponse user = userService.validateUser(email, password);
        if (user != null) {
            return ResponseEntity.ok(user); // 사용자 정보를 JSON 형태로 반환
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); // 실패 응답
        }
    }

    @PostMapping("/join")
    public ResponseEntity<NomalTokenResponse> joinNormalUser(@RequestBody UserCredential userCredential) {
        User existingUser = userService.findByEmail(userCredential.getEmail());
        // 이미 가입된 회원일 때
        if (existingUser != null) { // 사용자가 존재하는지 확인
            return new ResponseEntity<>(new NomalTokenResponse("User with the provided email already exists.", "nothing",""), HttpStatus.OK);
        }
        else{
            User newUser = userCredential.toUser(userCredential.getProviderName()); // 신규 유저 객체 생성
            userService.createUser(newUser);
            String token = userService.login(newUser.getNickName(), newUser.getEmail(), ""); // 토큰 생성
            String profileImage = newUser.getProfileImage();
            String nickName = newUser.getNickName();
            return new ResponseEntity<>(new NomalTokenResponse(token,profileImage,nickName), HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<NomalTokenResponse> loginNormalUser(@RequestBody UserLoginRequest dto){
        User existingUser = userService.findByEmail(dto.getEmail());
        System.out.println(dto.getEmail());
        if (existingUser != null) { // 사용자가 존재
            System.out.println("컨트롤러 지나감");
            String accessToken = userService.login(dto.getEmail(), dto.getNickName(), "");
            String profileImage = existingUser.getProfileImage();
            String nickName = existingUser.getNickName();
//            String refreshToken = userService.refresh(dto.getEmail(), dto.getNickName(), "");
            return ResponseEntity.ok().body(new NomalTokenResponse(accessToken, profileImage, nickName ));
        }
        else{
            // 사용자를 찾을 수 없는 경우, 적절한 상태 코드와 메시지를 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NomalTokenResponse("회원이 아닌 유저입니다.", "fail", ""));
        }
    }

    @PostMapping("/checkEmail")
    public boolean checkEmail(@RequestBody EmailRequest emailRequest) {
        return userService.validateByEmail(emailRequest.getEmail());
    }

}
