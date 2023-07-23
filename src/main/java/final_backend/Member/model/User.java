package final_backend.Member.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(nullable = false)
    private String nickName;
    private String userName;
    private Long kakaoId;
    private String email;
    private String password;
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.Normal;
    private String lover;
}