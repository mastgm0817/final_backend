package final_backend.Member.controller;

import final_backend.Member.model.User;
import final_backend.Member.model.UserJoinRequest;
import final_backend.Member.model.UserLoginRequest;
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

    @PostMapping("/users/join")
    public ResponseEntity<String> joinUser(@RequestBody UserJoinRequest userJoinRequest) {
        User existingUser = userService.findByEmail(userJoinRequest.getEmail());
        // 이미 가입된 회원일 때
        if (existingUser != null) { // 사용자가 존재하는지 확인
            return new ResponseEntity<>("User with the provided email already exists.", HttpStatus.OK);
        }
        else{
            User newUser = userJoinRequest.toUser(userJoinRequest.getProviderName()); // 신규 유저 객체 생성
            userService.createUser(newUser); // 유저 저장
            return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest dto){
        User existingUser = userService.findByEmail(dto.getEmail());
        if (existingUser != null) { // 사용자가 존재
            String token = userService.login(dto.getNickName(), "");
            return ResponseEntity.ok().body(new TokenResponse(token));
        }
        else{
            // 사용자를 찾을 수 없는 경우, 적절한 상태 코드와 메시지를 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new TokenResponse("회원이 아닌 유저입니다."));
        }
    }



}