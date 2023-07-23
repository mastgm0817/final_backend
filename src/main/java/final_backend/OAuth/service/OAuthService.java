package final_backend.OAuth.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import final_backend.Member.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OAuthService {

    @Autowired
    private UserRepository userRepository;

    private String client_id = System.getenv("CLIENT_ID");

    private String client_secret = System.getenv("CLIENT_SECRET");

//카카오 사용자 인증
    public Map<String, Object> getUserInfo(String code) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", "http://localhost:3000/oauth/kakao");
        params.add("code", code);
        params.add("client_secret", client_secret);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, httpHeaders);
        System.out.println("------------헤더--------------");
        System.out.println(kakaoTokenRequest.getHeaders());
        System.out.println("------------본문--------------");
        System.out.println(kakaoTokenRequest.getBody());


        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        System.out.println("------------response body--------------");
        System.out.println(response.getBody());
// TODO: 2023-07-24(024)


//        앱사용자 인증 부분
        JSONObject jo = new JSONObject(response.getBody());

        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();

        headers2.add("Authorization", "Bearer " + jo.get("access_token"));

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
        System.out.println("------------response2 body--------------");
        System.out.println(response2.getBody());
        Map<String, Object> userMap = new Gson().fromJson(response2.getBody(), new TypeToken<Map<String, Object>>(){}.getType());
        return userMap;
    }

//    public User saveUser(Map<String, Object> userMap) {
//
//        User user = userRepository.findByEmail();
//
//        //(3)
//        if (user == null) {
//            user = User.builder()
//                    .kakaoId(profile.getId())
//                    //(4)
//                    .kakaoProfileImg(profile.getKakao_account().getProfile().getProfile_image_url())
//                    .kakaoNickname(profile.getKakao_account().getProfile().getNickname())
//                    .kakaoEmail(profile.getKakao_account().getEmail())
//                    //(5)
//                    .userRole("ROLE_USER").build();
//
//            userRepository.save(user);
//        }
//    }
}
