package final_backend.Auth.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/kakao")
    public ResponseEntity<String> loginWithKakao() {
        // 카카오 로그인 처리 로직을 여기에 작성합니다.
        // 이 예제에서는 단순히 성공 메시지를 반환하도록 했습니다.
        return ResponseEntity.ok("Login with Kakao successful");
    }
}
