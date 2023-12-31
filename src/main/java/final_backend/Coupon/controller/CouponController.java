package final_backend.Coupon.controller;
import final_backend.Coupon.model.Coupon;
import final_backend.Coupon.service.CouponService;
import final_backend.Member.exception.CouponAlreadyAssignedException;
import final_backend.Member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/coupon")
public class CouponController {

    private final CouponService couponService;
    private final UserService userService;
    @Autowired
    public CouponController(CouponService couponService, UserService userService) {
        this.couponService = couponService;
        this.userService = userService;
    }

    @PostMapping // 쿠폰 한개만들때
    public ResponseEntity<List<Coupon>> createCoupon(@RequestBody Coupon coupon, @RequestParam Integer countNum) {
        List<Coupon> newCoupons = new ArrayList<>();
        System.out.println("값 잘 받아오는지 테스트" + coupon.getDiscountValue());
        for(int i = 0; i < countNum; i++) {
            Coupon newCoupon = couponService.createCoupon(coupon);
            newCoupons.add(newCoupon);
        }
        return new ResponseEntity<>(newCoupons, HttpStatus.CREATED);
    }

    @PostMapping("/{cpid}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long cpid) {
        try {
            couponService.deleteCoupon(cpid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while deleting the coupon");
        }
    }


    @GetMapping("/{cpid}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long cpid) {
        Coupon coupon = couponService.getCouponById(cpid);
        if (coupon != null) {
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = couponService.getAllCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    // applyCoupon 메서드에서 응답 생성
    @PostMapping("/use")
    public ResponseEntity<Map<String, Object>> applyCoupon(@RequestBody String couponCode) {
        Map<String, Object> response = new HashMap<>();
        System.out.println("프론트에서 넘어온 값: " + couponCode);

        // 쿠폰 검증 로직
        try {
            Coupon coupon = couponService.checkCouponValidity(couponCode);
            response.put("status", "ok");
            response.put("discountType", coupon.getDiscountType());
            response.put("discountValue", coupon.getDiscountValue());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CouponAlreadyAssignedException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/assign")
    public ResponseEntity<?> assignCouponToUser(@RequestParam String couponId, @RequestParam String nickName) {
        Long cpid = Long.parseLong(couponId);
        Long uid = userService.findByNickName(nickName).getUid();
        try {
            couponService.assignCouponToUser(cpid, uid);
            return new ResponseEntity<>("Coupon assigned successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @ExceptionHandler(CouponAlreadyAssignedException.class)
    public ResponseEntity<String> handleCouponAlreadyAssignedException(CouponAlreadyAssignedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}