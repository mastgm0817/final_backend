package final_backend.OAuth.model;

import final_backend.Member.model.User;
import lombok.Data;

@Data
public class SignupRequestDto {

    public String nickname;
    public String picture;
    public User user;
}