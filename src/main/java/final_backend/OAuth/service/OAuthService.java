package final_backend.OAuth.service;

import final_backend.OAuth.model.KakaoOAuthToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OAuthService {
//    private final WebClient webClient;
    private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private String REST_API_KEY = "25edd438130f1d799655087b02557293";

//    public OAuthService(WebClient webClient) {
//        this.webClient = webClient;
//    }
//
//    public Mono<String> getKakaoToken(String code) {
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", REST_API_KEY);
//        params.add("redirect_uri", "http://localhost:3000/oauth/kakao");
//        params.add("code", code);
//
//        return webClient.post()
//                .uri(KAKAO_TOKEN_URL)
//                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
//                .body(BodyInserters.fromFormData(params))
//                .retrieve()
//                .bodyToMono(KakaoOAuthToken.class)
//                .map(KakaoOAuthToken::getAccessToken);
//    }
}
