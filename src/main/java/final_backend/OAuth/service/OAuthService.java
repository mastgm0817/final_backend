package final_backend.OAuth.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import final_backend.Member.model.User;
import final_backend.Member.model.UserRole;
import final_backend.Member.model.kakao.KakaoAccountDto;
import final_backend.Member.model.kakao.KakaoTokenDto;
import final_backend.Member.repository.UserRepository;
import final_backend.OAuth.model.LoginResponseDto;
import final_backend.OAuth.model.SignupRequestDto;
import final_backend.OAuth.model.SignupResponseDto;
import final_backend.OAuth.model.token.model.RefreshToken;
import final_backend.OAuth.model.token.model.TokenDto;
import final_backend.OAuth.model.token.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class OAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private String client_id = System.getenv("CLIENT_ID");

    private String client_secret = System.getenv("CLIENT_SECRET");


    public KakaoTokenDto getKakaoAccessToken(String code) {
        RestTemplate rt = new RestTemplate(); //통신용
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", "http://localhost:3000/oauth/kakao");
        params.add("code", code);
        params.add("client_secret", client_secret);

        // 헤더와 바디 합치기 위해 HttpEntity 객체 생성
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        System.out.println(kakaoTokenRequest);

        // 카카오로부터 Access token 수신
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // JSON Parsing (-> KakaoTokenDto)
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoTokenDto kakaoTokenDto = null;
        try {
            kakaoTokenDto = objectMapper.readValue(accessTokenResponse.getBody(), KakaoTokenDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoTokenDto;
    }



    public KakaoAccountDto getKakaoInfo(String kakaoAccessToken) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> accountInfoRequest = new HttpEntity<>(headers);

        // POST 방식으로 API 서버에 요청 보내고, response 받아옴
        ResponseEntity<String> accountInfoResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                accountInfoRequest,
                String.class
        );

        System.out.println("카카오 서버에서 정상적으로 데이터를 수신했습니다.");
        // JSON Parsing (-> kakaoAccountDto)
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoAccountDto kakaoAccountDto = null;
        try {
            kakaoAccountDto = objectMapper.readValue(accountInfoResponse.getBody(), KakaoAccountDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(kakaoAccountDto);
        return kakaoAccountDto;
    }

    public User mapKakaoInfo(KakaoAccountDto accountDto) {

        // kakaoAccountDto 에서 필요한 정보 꺼내기
        Long kakaoId = accountDto.getId();
        String email = accountDto.getKakao_account().getEmail();
        String nickname = accountDto.getKakao_account().getProfile().getNickname();

        System.out.println("매핑한 정보 : " + email + ", " + nickname);
        // Account 객체에 매핑
        return User.builder()
                .kakaoId(kakaoId)
                .email(email)
                .nickName(nickname)
                .userRole(UserRole.Normal)
                .build();
    }
    public LoginResponseDto kakaoLogin(String kakaoAccessToken) {

        // kakaoAccessToken 으로 카카오 회원정보 받아옴
        KakaoAccountDto kakaoAccountDto = getKakaoInfo(kakaoAccessToken);
        String kakaoEmail = kakaoAccountDto.getKakao_account().getEmail();

        // kakaoAccountDto 를 Account 로 매핑
        User selectedUser = mapKakaoInfo(kakaoAccountDto);
        System.out.println("수신된 account 정보 : " + selectedUser);

        // 매핑만 하고 DB에 저장하질 않았으니까 Autogenerated 인 id가 null 로 나왔던거네 아... 오케오케 굿

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setKakaoAccessToken(kakaoAccessToken);
        // 가입되어 있으면 해당하는 User 객체를 응답
        // 존재하면 true + access token 을, 존재하지 않으면 False 리턴
        if (userRepository.existsByEmail(kakaoEmail)) {
            User resultUser = userRepository.findByEmail(kakaoEmail);
            loginResponseDto.setLoginSuccess(true);
            loginResponseDto.setKakaoAccessToken(kakaoAccessToken);
            loginResponseDto.setUser(resultUser);
            System.out.println("response setting: " + loginResponseDto);

            // 토큰 발급
//            TokenDto tokenDto = securityService.login(resultAccount.getId());
//            loginResponseDto.setAccessToken(tokenDto.getAccessToken());
            System.out.println("로그인은 확인됐고, 토큰을 발급은 아직 안했습니다.");
            return loginResponseDto;
        }
        else {
            // 닉네임, 프로필사진 set
            User newUser = new User();
            String nickName = selectedUser.getNickName();
            String email = selectedUser.getEmail();
            String profile = kakaoAccountDto.getKakao_account().getProfile().getProfile_image_url();
//            System.out.println(kakaoAccountDto);
//            System.out.println("--------1--------");
//            System.out.println(kakaoAccountDto.getKakao_account());
//            System.out.println("--------2--------");
//            System.out.println(kakaoAccountDto.getKakao_account().getProfile());
//            System.out.println("--------3--------");
//            System.out.println(kakaoAccountDto.getKakao_account().getProfile().getProfile_image_url());
//            System.out.println("--------4--------");
            newUser.setNickName(nickName);
            newUser.setEmail(email);
            newUser.setUserRole(UserRole.Normal);
            newUser.setProfileImage(profile);

            // DB에 save
            userRepository.save(newUser);
            LoginResponseDto newUserDto = new LoginResponseDto();
            newUserDto.setKakaoAccessToken(kakaoAccessToken);
            newUserDto.setUser(newUser);
            // 회원가입 결과로 회원가입한 accountId 리턴
            return newUserDto;
        }
    }
}
