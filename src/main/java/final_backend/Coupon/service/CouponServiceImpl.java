package final_backend.Coupon.service;

import final_backend.Coupon.model.Coupon;
import final_backend.Coupon.model.DiscountType;
import final_backend.Coupon.model.Status;
import final_backend.Coupon.repository.CouponRepository;
import final_backend.Member.exception.CouponAlreadyAssignedException;
import final_backend.Member.model.User;
import final_backend.Member.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  CouponRepository couponRepository;

    @Override
    public String generateCouponCode() {
        // 랜덤으로 12자리 숫자와 문자 조합으로 쿠폰 코드 생성
        return RandomStringUtils.randomAlphanumeric(12);
    }

    @Override
    @Transactional
    public Long createJoinCoupon(){
        Coupon newCoupon = new Coupon();
        newCoupon.setCode(generateCouponCode());
        newCoupon.setCouponContent("회원가입축하쿠폰");
        newCoupon.setDiscountType(DiscountType.PERCENTAGE);
        newCoupon.setDiscountValue(10L);
        newCoupon.setStatus(Status.DEFAULT);
        newCoupon.setCreatedAt(LocalDateTime.now());
        newCoupon.setUpdatedAt(LocalDateTime.now());
        newCoupon.setEndAt(LocalDateTime.now().plusMonths(1));
        couponRepository.save(newCoupon);
        return newCoupon.getCpid();
    }

    @Override
    public Coupon createCoupon(Coupon coupon) {
        Coupon newCoupon = new Coupon();
        // couponRequestDto에서 필요한 데이터를 Coupon 엔티티에 매핑
        newCoupon.setUserId(coupon.getUserId());
        newCoupon.setCode(generateCouponCode());
        newCoupon.setCouponContent(coupon.getCouponContent());
        if (coupon.getDiscountType() != null) {
            newCoupon.setDiscountType(coupon.getDiscountType());
        }
        System.out.println("Discount Value: " + coupon.getDiscountValue());

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


    @Override
    public Coupon getCouponById(Long cpid) {
        return couponRepository.findById(cpid).orElse(null);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Transactional
    public void assignCouponToUser(Long couponId, Long userId) throws CouponAlreadyAssignedException {
        // 유저와 쿠폰을 찾음
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new RuntimeException("Coupon not found"));

        if (coupon.getUserId() != null) {
            throw new CouponAlreadyAssignedException("Coupon already assigned");
        }
        // 쿠폰을 유저에게 할당
        coupon.setUserDTO(user);
        coupon.setUserId(user.getUid());
        coupon.setUserName(user.getNickName());
        coupon.setAssignedAt(LocalDateTime.now());
        // 유저의 쿠폰 리스트에 쿠폰 추가
        user.getCouponList().add(coupon);

    }
}