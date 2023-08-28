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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void deleteCoupon(Long cpid) throws Exception {
        Optional<Coupon> coupon = couponRepository.findById(cpid);
        if (coupon.isPresent()) {
            couponRepository.deleteById(cpid);
        } else {
            throw new Exception("Coupon not found");
        }
    }


    public Coupon checkCouponValidity(String couponCode) {
        couponCode = couponCode.replace("\"", "");
        Optional<Coupon> optionalCoupon = couponRepository.findByCode(couponCode);

        if (!optionalCoupon.isPresent()) {
            throw new CouponAlreadyAssignedException("쿠폰을 찾을 수 없습니다.");
        }

        Coupon coupon = optionalCoupon.get();

        if (coupon.getStatus() == Status.USING) {
            throw new CouponAlreadyAssignedException("현재 적용중인 쿠폰입니다.");
        }

        if (coupon.getStatus() == Status.USED) {
            throw new CouponAlreadyAssignedException("이미 사용한 쿠폰입니다.");
        }

        if (coupon.getEndAt().isAfter(LocalDateTime.now())) {
            // 쿠폰을 사용중 상태로 만듭니다.
            coupon.setStatus(Status.USING);
            coupon.setAssignedAt(LocalDateTime.now());  // 예약 시간을 설정
            couponRepository.save(coupon);
            return coupon;
        } else {
            throw new CouponAlreadyAssignedException("쿠폰이 만료되었습니다.");
        }
    }

    @Scheduled(fixedRate = 60000 * 5)  // 5분마다 실행
    public void releaseExpiredReservations() {
        List<Coupon> reservedCoupons = couponRepository.findAllByStatus(Status.USING);

        for (Coupon coupon : reservedCoupons) {
            if (coupon.getAssignedAt().plusMinutes(10).isBefore(LocalDateTime.now())) {
                coupon.setStatus(Status.DEFAULT);
                couponRepository.save(coupon);
            }
        }
    }




    @Override
    public Coupon getCouponById(Long cpid) {
        return couponRepository.findById(cpid).orElse(null);
    }


    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
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