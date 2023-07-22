package final_backend.Coupon.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cpid;

}