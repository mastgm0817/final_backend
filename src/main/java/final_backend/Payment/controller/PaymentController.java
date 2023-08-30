package final_backend.Payment.controller;

import final_backend.Coupon.service.CouponService;
import final_backend.Member.exception.BusinessLogicException;
import final_backend.Member.exception.ExceptionCode;
import final_backend.Member.service.UserService;
import final_backend.Payment.model.KakaoApproveResponse;
import final_backend.Payment.model.KakaoCancelResponse;
import final_backend.Payment.model.KakaoPaymentDTO;
import final_backend.Payment.model.KakaoReadyResponse;
import final_backend.Payment.service.KakaoPayService;
import final_backend.Payment.service.SharedDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/payment")
public class PaymentController {


    @Autowired
    private UserService userService;

    @Autowired
    private CouponService couponService;


    @Autowired
    private KakaoPayService kakaoPayService;

    @Autowired
    private SharedDTOService sharedDTOService;

    @Value("${weburl.secret}")
    private String weburl;


    @PostMapping("/ready")
    public KakaoReadyResponse readyToKakaoPay(@RequestBody Map<String, Object> OrderInfo) {

        System.out.println("Full Config: " + OrderInfo.toString());

        Map<String, Object> params = (Map<String, Object>) OrderInfo.get("params");
        Map<String, Object> couponInfo = (Map<String, Object>) OrderInfo.get("couponInfo");

        String kpaynickName = (String) OrderInfo.get("nickName");
        String kpayitemName = (String) params.get("item_name");
        String kpaycouponCode = (String) couponInfo.get("couponCode");

        return kakaoPayService.kakaoPayReady(OrderInfo);
    }

    @GetMapping("/success")
    public RedirectView afterPayRequest(@RequestParam("pg_token") String pgToken) {

        KakaoPaymentDTO dto = sharedDTOService.getKakaoPaymentDTO();

        String kpaynickName = dto.getKpaynickName();
        String kpayitemName = dto.getKpayitemName();
        String kpaycouponCode = dto.getKpaycouponCode();

        KakaoApproveResponse kakaoApprove = kakaoPayService.approveResponse(pgToken);


        userService.updateUserRoleAndVipTime(kpaynickName, kpayitemName);

        if (kpaycouponCode != null && !kpaycouponCode.isEmpty()) {
            couponService.usedCoupon(kpaycouponCode);
            System.out.println("쿠폰상태변환 성공");
        }
        System.out.println("구매성공");

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(weburl);
        return redirectView;

    }

    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public void cancel() {

        throw new BusinessLogicException(ExceptionCode.PAY_CANCEL);
    }

    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public void fail() {

        throw new BusinessLogicException(ExceptionCode.PAY_FAILED);
    }


    /**
     * 환불
     */
    @PostMapping("/refund")
    public ResponseEntity refund() {

        KakaoCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel();

        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }



    @PostMapping("/virtual")
    public ResponseEntity<String> handleVirtualPayment(@RequestBody Map<String, Object> config) {
        System.out.println("Full Config: " + config.toString());

        Map<String, Object> params = (Map<String, Object>) config.get("params");
        Map<String, Object> couponInfo = (Map<String, Object>) config.get("couponInfo");

        String nickName = (String) config.get("nickName");
        String itemName = (String) params.get("item_name");
        String couponCode = (String) couponInfo.get("couponCode");
        int totalAmount = (Integer) params.get("total_amount");

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



}
