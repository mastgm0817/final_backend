package final_backend.Coupon.controller;
import final_backend.Coupon.model.Coupon;
import final_backend.Coupon.model.CouponRequest;
import final_backend.Coupon.service.CouponService;
import final_backend.Member.exception.CouponAlreadyAssignedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/api/v1/coupon")
public class CouponController {

    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
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

//    @PostMapping
//    public ResponseEntity<List<Coupon>> createCoupons(@RequestBody CouponRequest couponRequest) {
//        List<Coupon> createdCoupons = new ArrayList<>();
//        for (int i = 0; i < couponRequest.getNumOfCoupons(); i++) {
//            createdCoupons.add(couponService.createCoupon(couponRequest.getCoupon()));
//        }
//        return new ResponseEntity<>(createdCoupons, HttpStatus.CREATED);
//    }

//    @PostMapping // 쿠폰 여러개 만들때
//    public ResponseEntity<List<Coupon>> createCoupons(@RequestBody List<Coupon> coupons) {
//        List<Coupon> newCoupons = new ArrayList<>();
//        for (Coupon coupon : coupons) {
//            newCoupons.add(couponService.createCoupon(coupon));
//        }
//        return new ResponseEntity<>(newCoupons, HttpStatus.CREATED);
//    }


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

    @PostMapping("/assign")
    public ResponseEntity<?> assignCouponToUser(@RequestParam String couponId, @RequestParam String userId) {
        Long cpid = Long.parseLong(couponId);
        Long uid = Long.parseLong(userId);
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