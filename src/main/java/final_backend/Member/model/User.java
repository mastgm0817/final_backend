package final_backend.Member.model;

import final_backend.Admin.model.UserBlackListDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name="user_table")
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
    private boolean blocked;

    @Enumerated(EnumType.STRING)
    @Default
    private UserRole userRole = UserRole.Normal;
    private String lover;

    public UserCredentialResponse toUser() {
        return UserCredentialResponse.builder()
                .uid(uid)
                .email(email)
                .nickName(nickName)
                .profileImage(profileImage)
                .build();
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImage = profileImageUrl;
    }
    @OneToOne(mappedBy = "blockUser", cascade = CascadeType.ALL)
    private UserBlackListDTO blackListDetails;

}