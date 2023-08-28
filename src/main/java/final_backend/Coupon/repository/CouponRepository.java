package final_backend.Coupon.repository;


import final_backend.Coupon.model.Coupon;
import final_backend.Coupon.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Coupon getCouponByCpid(Long cpid);
    Optional<Coupon> findByCode(String code);

    void deleteByUserId(Long userId);

    List<Coupon> findAllByStatus(Status status);
}
