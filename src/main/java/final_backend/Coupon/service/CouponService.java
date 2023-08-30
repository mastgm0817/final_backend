package final_backend.Coupon.service;

import final_backend.Coupon.model.Coupon;
import final_backend.Member.exception.CouponAlreadyAssignedException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CouponService {
    String generateCouponCode();

    @Transactional
    Long createJoinCoupon();

    Coupon createCoupon(Coupon coupon);

    void deleteCoupon(Long cpid) throws Exception;

    Coupon checkCouponValidity(String couponCode);

    Coupon getCouponById(Long cpid);

    List<Coupon> getAllCoupons();

    @Transactional
    void assignCouponToUser(Long couponId, Long userId) throws CouponAlreadyAssignedException;

    void usedCoupon(String couponCode);
}
