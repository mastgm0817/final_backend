package final_backend.Member.controller;

import final_backend.Member.exception.BlockedUserException;
import final_backend.Member.exception.UserNotFoundException;
import final_backend.Member.model.UserClickRequest;
import final_backend.Member.model.UserClickResponse;
import final_backend.Member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ClickController {
    @Autowired
    private UserService userService;  // 이 서비스는 카운트와 권한 로직을 처리

    @PostMapping("/click")
    public ResponseEntity<UserClickResponse> handleClick(@RequestBody UserClickRequest request) {
        try {
            UserClickResponse response = userService.handleClick(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // 에러 메시지를 담은 새로운 UserClickResponse 객체 생성
            UserClickResponse response = new UserClickResponse();
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}