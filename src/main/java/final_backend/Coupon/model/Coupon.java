package final_backend.Coupon.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import final_backend.Board.model.Board;
import final_backend.Member.model.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cpid;

    private Long userId;

    private String userName;

    @Column(nullable = false)
    private String code;

    private String couponContent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private Long discountValue;

    @Enumerated(EnumType.STRING)
    private Status status = Status.DEFAULT;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime endAt;

    private LocalDateTime assignedAt;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User userDTO;

    // Coupon 클래스의 toString 메서드에서 userDTO를 출력하지 않도록 수정
    @Override
    public String toString() {
        return "Coupon{" +
                "cpid=" + cpid +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", code='" + code + '\'' +
                ", couponContent='" + couponContent + '\'' +
                ", discountType=" + discountType +
                ", discountValue=" + discountValue +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", endAt=" + endAt +
                ", assignedAt=" + assignedAt +
                '}';
    }

}
