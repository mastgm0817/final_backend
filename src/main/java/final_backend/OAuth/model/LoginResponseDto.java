package final_backend.OAuth.model;

import final_backend.Member.model.User;
import lombok.Data;

@Data
public class LoginResponseDto {

    public boolean loginSuccess;
    public User user;
    public String kakaoAccessToken;
}