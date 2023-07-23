package final_backend.OAuth.controller;

import final_backend.OAuth.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private OAuthService oauthService;


    @GetMapping("/kakao")
    public ResponseEntity<Map<String, Object>> KakaoLogin(@RequestParam("code") String code, HttpServletRequest request) {
        Map<String, Object> userMap = oauthService.getUserInfo(code);
//        log.debug("###### kakao login = {}", userMap);
//        oauthService.saveUser(userMap);
        // 이후 유저 여부를 판단하고 회원가입 / 로그인 처리를 진행하면된다

        return new ResponseEntity<>(userMap, HttpStatus.OK);
    }
}
