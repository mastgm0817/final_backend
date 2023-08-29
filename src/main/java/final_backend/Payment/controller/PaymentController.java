package final_backend.Payment.controller;

import final_backend.Payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/kakao")
    public String requestKakaoPay(@RequestBody Map<String, Object> payinfo) {
        return paymentService.requestKakaoPay(payinfo);
    }


}
