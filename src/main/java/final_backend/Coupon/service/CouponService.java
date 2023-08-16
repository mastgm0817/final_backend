package final_backend.Coupon.service;

import final_backend.Coupon.model.Coupon;

import java.util.List;

public interface CouponService {
    String generateCouponCode();

    Coupon createCoupon(Coupon coupon);

    Coupon getCouponById(Long cpid);

    List<Coupon> getAllCoupons();
}
