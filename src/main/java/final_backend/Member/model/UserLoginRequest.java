package final_backend.Member.model;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String nickName;
    private String email;
    private String providerName;
}
