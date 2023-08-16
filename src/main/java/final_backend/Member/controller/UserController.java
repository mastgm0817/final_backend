package final_backend.Member.controller;

import final_backend.Member.model.*;
import final_backend.Member.service.UserService;
import final_backend.Utils.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

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

    @DeleteMapping("/users/{uid}")
    public ResponseEntity<Void> deleteUser(@PathVariable String uid) {
        Long num = Long.valueOf(uid);
        boolean deleted = userService.deleteUser(num);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
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

    @PostMapping("/normal/users/authorize")
    public ResponseEntity<?> checkEmailPasswd(@RequestBody UserCredentialRequest userCredentialRequest) {
        String email = userCredentialRequest.getEmail();
        String password = userCredentialRequest.getPassword();

        User user = userService.validateUser(email, password);

        if (user != null) {
            return ResponseEntity.ok(user); // 사용자 정보를 JSON 형태로 반환
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); // 실패 응답
        }
    }

    @PostMapping("/normal/users/join")
    public ResponseEntity<TokenResponse> joinNormalUser(@RequestBody UserCredential userCredential) {
        User existingUser = userService.findByEmail(userCredential.getEmail());
        // 이미 가입된 회원일 때
        if (existingUser != null) { // 사용자가 존재하는지 확인
            return new ResponseEntity<>(new TokenResponse("User with the provided email already exists."), HttpStatus.OK);
        }
        else{
            User newUser = userCredential.toUser(userCredential.getProviderName()); // 신규 유저 객체 생성
            userService.createUser(newUser);
            String token = userService.login(newUser.getNickName(), newUser.getEmail(), ""); // 토큰 생성
            return new ResponseEntity<>(new TokenResponse(token), HttpStatus.CREATED);
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
            userService.createUser(newUser);
            String token = userService.login(newUser.getNickName(), newUser.getEmail(), ""); // 토큰 생성
            return new ResponseEntity<>(new TokenResponse(token), HttpStatus.CREATED);
        }
    }


    @PostMapping("/normal/users/login")
    public ResponseEntity<TokenResponse> loginNormalUser(@RequestBody UserLoginRequest dto){
        User existingUser = userService.findByEmail(dto.getEmail());
        System.out.println(dto.getEmail());
        if (existingUser != null) { // 사용자가 존재
            System.out.println("컨트롤러 지나감");
            String accessToken = userService.login(dto.getEmail(), dto.getNickName(), "");
//            String refreshToken = userService.refresh(dto.getEmail(), dto.getNickName(), "");
            return ResponseEntity.ok().body(new TokenResponse(accessToken));
        }
        else{
            // 사용자를 찾을 수 없는 경우, 적절한 상태 코드와 메시지를 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new TokenResponse("회원이 아닌 유저입니다."));
        }
    }





    @PostMapping("/users/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest dto){
        User existingUser = userService.findByEmail(dto.getEmail());
        System.out.println(dto.getEmail());
        if (existingUser != null) { // 사용자가 존재
            System.out.println("컨트롤러 지나감");
            String accessToken = userService.login(dto.getEmail(), dto.getNickName(), "");
//            String refreshToken = userService.refresh(dto.getEmail(), dto.getNickName(), "");
            return ResponseEntity.ok().body(new TokenResponse(accessToken));
        }
        else{
            // 사용자를 찾을 수 없는 경우, 적절한 상태 코드와 메시지를 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new TokenResponse("회원이 아닌 유저입니다."));
        }
    }



}