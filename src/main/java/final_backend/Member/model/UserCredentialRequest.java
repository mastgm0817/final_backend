package final_backend.Member.model;

import lombok.Data;

@Data
public class UserCredentialRequest {
    private String nickName;
    private String email;
    private String provider;
    private String password;
}
