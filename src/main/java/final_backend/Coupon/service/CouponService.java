package final_backend.Coupon.service;

import final_backend.Coupon.model.Coupon;
import final_backend.Coupon.model.Status;
import final_backend.Coupon.repository.CouponRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Autowired
    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }
    // 다른 필요한 로직과 함께 쿠폰 코드를 생성하는 메소드 추가

    public String generateCouponCode() {
        // 랜덤으로 12자리 숫자와 문자 조합으로 쿠폰 코드 생성
        return RandomStringUtils.randomAlphanumeric(12);
    }


    // CouponService.java

    public Coupon createCoupon(Coupon coupon) {
        Coupon newCoupon = new Coupon();
        // couponRequestDto에서 필요한 데이터를 Coupon 엔티티에 매핑
        newCoupon.setUserId(coupon.getUserId());
        newCoupon.setCode(generateCouponCode());
        newCoupon.setCouponContent(coupon.getCouponContent());
        newCoupon.setDiscountType(coupon.getDiscountType());
        newCoupon.setDiscountValue(coupon.getDiscountValue());
        newCoupon.setStatus(Status.DEFAULT);
        newCoupon.setCreatedAt(LocalDateTime.now());
        newCoupon.setUpdatedAt(LocalDateTime.now());
        newCoupon.setEndAt(coupon.getEndAt());
        newCoupon.setAssignedAt(coupon.getAssignedAt());

        // Coupon 엔티티를 저장하고 저장된 엔티티를 반환
        return couponRepository.save(newCoupon);
    }

    // 새로운 메서드 추가: 여러 개의 쿠폰을 생성하는 기능
//    public List<Coupon> createCoupons(List<Coupon> coupons) {
//        List<Coupon> createdCoupons = new ArrayList<>();
//        for (Coupon coupon : coupons) {
//            Coupon createdCoupon = createCoupon(coupon);
//            createdCoupons.add(createdCoupon);
//        }
//        return createdCoupons;
//    }


    public Coupon getCouponById(Long cpid) {
        return couponRepository.findById(cpid).orElse(null);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    // 다른 비즈니스 로직들...
}