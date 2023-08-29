package final_backend.Payment.controller;

import final_backend.Payment.model.KakaoApproveResponse;
import final_backend.Payment.model.KakaoReadyResponse;
import final_backend.Payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/ready")
    public KakaoReadyResponse readyToKakaoPay() {

        return paymentService.kakaoPayReady();
    }

    /**
     * 결제 성공
     */
    @GetMapping("/success")
    public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken) {

        KakaoApproveResponse kakaoApprove = paymentService.ApproveResponse(pgToken);

        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }


}
