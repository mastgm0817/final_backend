package final_backend.Member.model;

import final_backend.Admin.model.UserBlackListDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import final_backend.Coupon.model.Coupon;
//import final_backend.Inquiry.model.InquiryDTO;
import final_backend.Inquiry.model.InquiryDTO;
import final_backend.Wishlist.model.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime vipStartTime;
    private LocalDateTime vipEndTime;
    private LocalDateTime lastClickTime;
    @Builder.Default
    private Integer clickCount = 0;
    @Builder.Default
    private Boolean isBlocked = false;

    @Enumerated(EnumType.STRING)
    @Default
    private UserRole userRole = UserRole.Normal;
    private String lover;

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "userDTO", cascade = CascadeType.ALL)
    private List<Coupon> couponList = new ArrayList<>();

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "userWish", cascade = CascadeType.ALL)
    private List<Course> dateCourses = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "blockUser", cascade = CascadeType.ALL)
    private UserBlackListDTO blackListDetails;

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
     List<InquiryDTO> inquiryList = new ArrayList<>();

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


    @PrePersist
    public void prePersist() {
        this.clickCount = this.clickCount == null ? 0 : this.clickCount;
        this.isBlocked = this.isBlocked == null ? false : this.isBlocked;
    }
    // User 클래스의 toString 메서드에서 couponList와 blackListDetails를 출력하지 않도록 수정
    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", providerName='" + providerName + '\'' +
                ", userRole=" + userRole +
                ", lover='" + lover + '\'' +
                ", vipStartTime=" + vipStartTime +
                ", vipEndTime=" + vipEndTime +
                ", lastClickTime=" + lastClickTime +
                ", clickCount=" + clickCount +
                ", isBlocked=" + isBlocked +
                '}';
    }




}