package final_backend.Coupon.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cpid;

    @Column(nullable = false)
    private String name;
    private String email;
    private String picture;
    private String role = "ROLE_USER";
    private String lover;
}