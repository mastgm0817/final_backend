package final_backend.OAuth.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import final_backend.OAuth.service.OAuthService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/oauth")
public class OAuthController {

    private static final Logger log = LoggerFactory.getLogger(OAuthController.class);

    @Autowired
    private OAuthService oauthService;

    @GetMapping("/kakao")
    public ResponseEntity<Map<String, Object>> KakaoLogin(@RequestParam("code") String code, HttpServletRequest request) {

        RestTemplate rt = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "25edd438130f1d799655087b02557293");
        params.add("redirect_uri", "http://localhost:3000/oauth/kakao");
        params.add("code", code);
        params.add("client_secret", "pcIl6N5qw8Y7s4OtUNZyVpep0xEuVmxf");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // 토큰값 Json 형식으로 가져오기위해 생성
        JSONObject jo = new JSONObject(response.getBody());

        // 토큰결과값
        log.debug("kakao token result = {} " , response);

        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();

        headers2.add("Authorization", "Bearer " + jo.get("access_token"));
//        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );


        // 토큰을 사용하여 사용자 정보 추출
//        JSONObject jo2 = new JSONObject(response2.getBody());
//        log.debug("###### kakao login = {}", jo2);
//
//        // 이후 유저 여부를 판단하고 회원가입 / 로그인 처리를 진행하면된다
//
//        return new ResponseEntity<>(jo2, HttpStatus.OK);

        Map<String, Object> userMap = new Gson().fromJson(response2.getBody(), new TypeToken<Map<String, Object>>(){}.getType());
        log.debug("###### kakao login = {}", userMap);

        // 이후 유저 여부를 판단하고 회원가입 / 로그인 처리를 진행하면된다

        return new ResponseEntity<>(userMap, HttpStatus.OK);
    }

}
