package final_backend.Member.model;

import lombok.Data;

@Data
public class UserCredential {
    private String nickName;
    private String email;
    private String providerName;
    private String password;

    public User toUser(String providerName) {
        return User.builder()
                .nickName(nickName)
                .providerName((providerName))
                .email(email)
                .password(password) // 암호화 적용
                .userRole(UserRole.Normal) // 기본 역할 설정
                .build();
    }
}
