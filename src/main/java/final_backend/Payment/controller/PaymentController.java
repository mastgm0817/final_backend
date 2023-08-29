package final_backend.Payment.controller;

import final_backend.Coupon.service.CouponService;
import final_backend.Member.service.UserService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private CouponService couponService;

    @PostMapping("/ready")
    public KakaoReadyResponse readyToKakaoPay() {

        return paymentService.kakaoPayReady();
    }

    @PostMapping("/virtual")
    public ResponseEntity<String> handleVirtualPayment(@RequestBody Map<String, Object> config) {
        System.out.println("Full Config: " + config.toString());

        Map<String, Object> params = (Map<String, Object>) config.get("params");
        Map<String, Object> couponInfo = (Map<String, Object>) config.get("couponInfo");

        String nickName = (String) config.get("nickName");
        String itemName = (String) params.get("item_name");
        int totalAmount = (Integer) params.get("total_amount");
        String couponCode = (String) couponInfo.get("couponCode");

        System.out.println("Item Name: " + itemName);
        System.out.println("nickName: " + nickName);
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Coupon Code: " + couponCode);

        try {
            userService.updateUserRoleAndVipTime(nickName, itemName);

            if (couponCode != null && !couponCode.isEmpty()) {
                couponService.usedCoupon(couponCode);
            }
            return ResponseEntity.ok("구매에 성공하였습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
