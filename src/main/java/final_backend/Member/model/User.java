package final_backend.Member.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import final_backend.Coupon.model.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "userDTO", cascade = CascadeType.ALL)
    List<Coupon> couponList = new ArrayList<>();

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

}