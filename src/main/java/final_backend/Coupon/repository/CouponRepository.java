package final_backend.Coupon.repository;


import final_backend.Coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Coupon getCouponByCpid(Long cpid);
}
