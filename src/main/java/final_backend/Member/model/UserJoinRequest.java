package final_backend.Member.model;

import lombok.Data;

@Data
public class UserJoinRequest {
    private String provider; // 추가된 필드
    private String nickName;
    private String email;
    private String profileImage;

    public User toUser() {
        return User.builder()
                .nickName(nickName)
                .email(email)
                .profileImage(profileImage)
                .userRole(UserRole.Normal) // 기본 역할 설정
                .build();
    }

}