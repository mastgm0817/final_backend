package final_backend.Payment.service;

import final_backend.Payment.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoPayService {

    @Autowired
    private SharedDTOService sharedDTOService;

    @Value("${kakao.admin.key}")
    private String kakaoAdminKey;
    static final String cid = "TC0ONETIME";
    private KakaoReadyResponse kakaoReady;

    public KakaoReadyResponse kakaoPayReady(Map<String, Object> OrderInfo) {



        Map<String, Object> params = (Map<String, Object>) OrderInfo.get("params");
        Map<String, Object> couponInfo = (Map<String, Object>) OrderInfo.get("couponInfo");

        KakaoPaymentDTO dto = new KakaoPaymentDTO();
        dto.setKpaynickName((String) OrderInfo.get("nickName"));
        dto.setKpayitemName((String) params.get("item_name"));
        dto.setKpaycouponCode((String) couponInfo.get("couponCode"));

        sharedDTOService.setKakaoPaymentDTO(dto);

        System.out.println("레디 서비스" + dto);

        // payInfo 안에 있는 params를 가져옴
        Map<String, Object> paramsFromPayInfo = (Map<String, Object>) OrderInfo.get("params");

        // LinkedMultiValueMap을 사용하여 데이터 저장
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", paramsFromPayInfo.get("partner_order_id").toString());
        parameters.add("partner_user_id", paramsFromPayInfo.get("partner_user_id").toString());
        parameters.add("item_name", paramsFromPayInfo.get("item_name").toString());
        parameters.add("quantity", paramsFromPayInfo.get("quantity").toString());
        parameters.add("total_amount", paramsFromPayInfo.get("total_amount").toString());
        parameters.add("tax_free_amount", paramsFromPayInfo.get("tax_free_amount").toString());
        parameters.add("approval_url", paramsFromPayInfo.get("approval_url").toString());
        parameters.add("fail_url", paramsFromPayInfo.get("fail_url").toString());
        parameters.add("cancel_url", paramsFromPayInfo.get("cancel_url").toString());



        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponse.class);

        return kakaoReady;
    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + kakaoAdminKey;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }

    /**
     * 결제 완료 승인
     */
    public KakaoApproveResponse approveResponse(String pgToken) {

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoReady.getTid());
        parameters.add("partner_order_id", "partner_order_id");
        parameters.add("partner_user_id", "partner_user_id");
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponse approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponse.class);

        return approveResponse;
    }

    /**
     * 결제 환불
     */
    public KakaoCancelResponse kakaoCancel() {

        // 카카오페이 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", "환불할 결제 고유 번호");
        parameters.add("cancel_amount", "환불 금액");
        parameters.add("cancel_tax_free_amount", "환불 비과세 금액");
        parameters.add("cancel_vat_amount", "환불 부가세");

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoCancelResponse cancelResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/cancel",
                requestEntity,
                KakaoCancelResponse.class);

        return cancelResponse;
    }

}

