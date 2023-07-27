package final_backend.OAuth.controller;

import final_backend.Member.model.kakao.KakaoTokenDto;
import final_backend.OAuth.model.LoginResponseDto;
import final_backend.OAuth.model.SignupRequestDto;
import final_backend.OAuth.model.SignupResponseDto;
import final_backend.OAuth.model.token.model.TokenDto;
import final_backend.OAuth.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private OAuthService oauthService;


    @GetMapping("/kakao")
    public ResponseEntity<LoginResponseDto> kakaoLogin(HttpServletRequest request) {

        String code = request.getParameter("code");
//        System.out.println(code);
        KakaoTokenDto kakaoTokenDto = oauthService.getKakaoAccessToken(code);
        System.out.println("kakaoTokenDto: " + kakaoTokenDto);
        String kakaoAccessToken = kakaoTokenDto.getAccess_token();
        System.out.println("kakaoAccessToken: " + kakaoAccessToken);

        // 토큰 발급까지 authService.kakaologin 상에서 다 처리하자
        LoginResponseDto loginResponseDto = oauthService.kakaoLogin(kakaoAccessToken);

        return ResponseEntity.ok(loginResponseDto);
    }

}
