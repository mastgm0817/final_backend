package final_backend.Payment.service;


import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PaymentService {

    public String requestKakaoPay(Map<String, Object> payinfo) {
        RestTemplate restTemplate = new RestTemplate();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "a25ce36106405bcb194b783daf42728b");
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", payinfo.get("cid").toString());
        params.add("partner_order_id", payinfo.get("partner_order_id").toString());
        params.add("partner_user_id", payinfo.get("partner_user_id").toString());
        params.add("item_name", payinfo.get("item_name").toString());
        params.add("quantity", payinfo.get("quantity").toString());
        params.add("total_amount", payinfo.get("total_amount").toString());
        params.add("vat_amount", payinfo.get("vat_amount").toString());
        params.add("approval_url", payinfo.get("approval_url").toString());
        params.add("fail_url", payinfo.get("fail_url").toString());
        params.add("cancel_url", payinfo.get("cancel_url").toString());

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        // API 호출
        ResponseEntity<Map> response = restTemplate.exchange("https://kapi.kakao.com/v1/payment/ready", HttpMethod.POST, body, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            String next_redirect_pc_url = responseBody.get("next_redirect_pc_url").toString();
            return next_redirect_pc_url;
        } else {
            // 실패 처리
            return "Failed";
        }
    }
}
