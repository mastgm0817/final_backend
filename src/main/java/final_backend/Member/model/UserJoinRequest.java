package final_backend.Member.model;

import lombok.Data;

@Data
public class UserJoinRequest {
    private String providerName;
    private String nickName;
    private String email;
    private String profileImage;

    public User toUser(String providerName) {
        return User.builder()
                .nickName(nickName)
                .providerName((providerName))
                .email(email)
                .profileImage(profileImage)
                .userRole(UserRole.Normal) // 기본 역할 설정
                .build();
    }

}