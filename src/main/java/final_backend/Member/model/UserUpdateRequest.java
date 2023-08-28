package final_backend.Member.model;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String email;
    private String nickName;
    private String userName;
}
