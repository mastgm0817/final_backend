package final_backend.Member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
//빌더를 설치하면 기본 생성자가 사라져서 JPA기능이 제대로 안할수있음
//해결법은 Constructor를 아래와 같이 선언해주는것
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(nullable = false)
    private String nickName;
    private String userName;
    private String email;
    private String password;
    private String profileImage;

    private String providerName;

    @Enumerated(EnumType.STRING)
    @Default
    private UserRole userRole = UserRole.Normal;
    private String lover;


}