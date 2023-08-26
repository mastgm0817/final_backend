package final_backend.Admin.model;

import final_backend.Member.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="black_List")
public class UserBlackListDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long blackId;

    private String reason;
    @Column(nullable = false)
    private LocalDateTime startDate; // 블랙된 시작일
    private LocalDateTime endDate; // 블랙된 종료일 (null일 수 있음. 예: 영구적으로 블랙)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User blockUser;
}
