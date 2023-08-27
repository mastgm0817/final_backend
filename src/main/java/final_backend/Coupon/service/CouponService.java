package final_backend.Coupon.service;

import final_backend.Coupon.model.Coupon;
import final_backend.Member.model.User;

import java.util.List;

public interface CouponService {
    String generateCouponCode();

    Long createJoinCoupon();

    Coupon createCoupon(Coupon coupon);

    Coupon getCouponById(Long cpid);

    List<Coupon> getAllCoupons();

    void deleteCoupon(Long cpid) throws Exception;

    void assignCouponToUser(Long couponId, Long userId);
}
